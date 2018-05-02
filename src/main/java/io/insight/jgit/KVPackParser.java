package io.insight.jgit;

import io.insight.jgit.services.KVObjectService;
import org.eclipse.jgit.internal.storage.file.PackLock;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.LongMap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.zip.CRC32;

public class KVPackParser extends PackParser {

  private final KVObjectService objectService;

  private HashMap<Long, ObjectInfo> objectsByPos = new HashMap<>();
  private final CRC32 crc;
  private RandomAccessFile out;

  public KVPackParser(KVObjectService objectService,
                      KVObjectDatabase objectDatabase,
                      InputStream in) {
    super(objectDatabase, in);
    this.objectService = objectService;
    this.crc = new CRC32();
  }

  @Override
  public PackLock parse(ProgressMonitor receiving, ProgressMonitor resolving) throws IOException {
    File tmpPack = File.createTempFile("incoming_", ".pack");
    out = new RandomAccessFile(tmpPack, "rw");
    super.parse(receiving, resolving);

    for (ObjectInfo info : objectsByPos.values()) {
      objectService.insertPackedObject(info.objectId, info.type, info.inflatedSize, info.getBaseId(),
          out.getChannel(), info.pos + info.headerSize, info.size);
    }
    if (!tmpPack.delete() && tmpPack.exists())
      tmpPack.deleteOnExit();
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onStoreStream(byte[] raw, int pos, int len)
      throws IOException {
    out.write(raw, pos, len);
  }


  @Override
  protected void onObjectHeader(Source src, byte[] raw, int pos, int len) throws IOException {
    crc.update(raw, pos, len);
    if (last != null) {
      last.headerSize = len;
    }
  }

  @Override
  protected void onObjectData(Source src, byte[] raw, int pos, int len) throws IOException {
    crc.update(raw, pos, len);
  }

  @Override
  protected void onEndThinPack() throws IOException {
  }

  @Override
  protected void onPackHeader(long objCnt) throws IOException {
  }

  @Override
  protected void onPackFooter(byte[] hash) throws IOException {
    if (last != null) {
      last.size = out.getFilePointer() - last.pos;
    }
  }


  @Override
  protected ObjectTypeAndSize seekDatabase(UnresolvedDelta delta,
                                           ObjectTypeAndSize info) throws IOException {
    out.seek(delta.getOffset());
    crc.reset();
    return readObjectHeader(info);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ObjectTypeAndSize seekDatabase(PackedObjectInfo obj,
                                           ObjectTypeAndSize info) throws IOException {
    out.seek(obj.getOffset());
    crc.reset();
    return readObjectHeader(info);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int readDatabase(byte[] dst, int pos, int cnt) throws IOException {
    return out.read(dst, pos, cnt);
  }

  @Override
  protected boolean checkCRC(int oldCRC) {
    return oldCRC == (int) crc.getValue();
  }

  @Override
  protected void onBeginWholeObject(long streamPosition, int type, long inflatedSize) throws IOException {
    crc.reset();
    newObject(streamPosition, type, inflatedSize);
  }

  private ObjectInfo last;

  private ObjectInfo newObject(long streamPosition, int type, long inflatedSize) {
    if (last != null) {
      last.size = streamPosition - last.pos;
    }
    last = new ObjectInfo();
    last.pos = streamPosition;
    last.type = type;
    last.inflatedSize = inflatedSize;
    objectsByPos.put(streamPosition, last);
    return last;
  }


  @Override
  protected void onEndWholeObject(PackedObjectInfo info) throws IOException {
    info.setCRC((int) crc.getValue());
  }

  @Override
  protected void onInflatedObjectData(PackedObjectInfo info, int typeCode, byte[] data) throws IOException {
    ObjectInfo i = objectsByPos.get(info.getOffset());
    i.objectId = info.getName();
    i.type = typeCode;
  }

  @Override
  protected boolean onAppendBase(int typeCode, byte[] data, PackedObjectInfo info) throws IOException {

    return true;
  }

  @Override
  protected void onBeginOfsDelta(long deltaStreamPosition, long baseStreamPosition, long inflatedSize) throws IOException {
    ObjectInfo o = newObject(deltaStreamPosition, Constants.OBJ_OFS_DELTA, inflatedSize);
    o.basePos = baseStreamPosition;
  }

  @Override
  protected void onBeginRefDelta(long deltaStreamPosition, AnyObjectId baseId, long inflatedSize) throws IOException {
    ObjectInfo o = newObject(deltaStreamPosition, Constants.OBJ_REF_DELTA, inflatedSize);
    o.baseId = baseId.name();
  }

  class ObjectInfo {
    long basePos;
    String baseId;
    String objectId;
    int headerSize;
    long pos;
    int type;
    long inflatedSize;
    long size;

    String getBaseId() {
      if (baseId == null) {
        return objectsByPos.get(basePos).objectId;
      }
      return baseId;
    }
  }
}
