/*
 * This file is generated by jOOQ.
*/
package io.insight.jgit.jdbc.jooq.tables;


import io.insight.jgit.jdbc.jooq.Test;
import io.insight.jgit.jdbc.jooq.tables.records.GitObjectsRecord;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GitObjects extends TableImpl<GitObjectsRecord> {

    private static final long serialVersionUID = -1408287752;

    /**
     * The reference instance of <code>test.git_objects</code>
     */
    public static final GitObjects GIT_OBJECTS = new GitObjects();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GitObjectsRecord> getRecordType() {
        return GitObjectsRecord.class;
    }

    /**
     * The column <code>test.git_objects.repo</code>.
     */
    public final TableField<GitObjectsRecord, String> REPO = createField("repo", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>test.git_objects.object_id</code>.
     */
    public final TableField<GitObjectsRecord, String> OBJECT_ID = createField("object_id", org.jooq.impl.SQLDataType.CHAR(40), this, "");

    /**
     * The column <code>test.git_objects.type</code>.
     */
    public final TableField<GitObjectsRecord, Integer> TYPE = createField("type", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>test.git_objects.data</code>.
     */
    public final TableField<GitObjectsRecord, byte[]> DATA = createField("data", org.jooq.impl.SQLDataType.BLOB, this, "");

    /**
     * The column <code>test.git_objects.base</code>.
     */
    public final TableField<GitObjectsRecord, String> BASE = createField("base", org.jooq.impl.SQLDataType.CHAR(40), this, "");

    /**
     * The column <code>test.git_objects.size</code>.
     */
    public final TableField<GitObjectsRecord, Long> SIZE = createField("size", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>test.git_objects.total_size</code>.
     */
    public final TableField<GitObjectsRecord, Long> TOTAL_SIZE = createField("total_size", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * Create a <code>test.git_objects</code> table reference
     */
    public GitObjects() {
        this(DSL.name("git_objects"), null);
    }

    /**
     * Create an aliased <code>test.git_objects</code> table reference
     */
    public GitObjects(String alias) {
        this(DSL.name(alias), GIT_OBJECTS);
    }

    /**
     * Create an aliased <code>test.git_objects</code> table reference
     */
    public GitObjects(Name alias) {
        this(alias, GIT_OBJECTS);
    }

    private GitObjects(Name alias, Table<GitObjectsRecord> aliased) {
        this(alias, aliased, null);
    }

    private GitObjects(Name alias, Table<GitObjectsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Test.TEST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GitObjects as(String alias) {
        return new GitObjects(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GitObjects as(Name alias) {
        return new GitObjects(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GitObjects rename(String name) {
        return new GitObjects(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GitObjects rename(Name name) {
        return new GitObjects(name, null);
    }
}
