/*
 * This file is generated by jOOQ.
*/
package io.insight.jgit.jdbc.jooq;


import io.insight.jgit.jdbc.jooq.tables.GitConfig;
import io.insight.jgit.jdbc.jooq.tables.GitObjects;
import io.insight.jgit.jdbc.jooq.tables.GitRefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


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
public class Test extends SchemaImpl {

    private static final long serialVersionUID = -1384263792;

    /**
     * The reference instance of <code>test</code>
     */
    public static final Test TEST = new Test();

    /**
     * The table <code>test.git_config</code>.
     */
    public final GitConfig GIT_CONFIG = io.insight.jgit.jdbc.jooq.tables.GitConfig.GIT_CONFIG;

    /**
     * The table <code>test.git_objects</code>.
     */
    public final GitObjects GIT_OBJECTS = io.insight.jgit.jdbc.jooq.tables.GitObjects.GIT_OBJECTS;

    /**
     * The table <code>test.git_refs</code>.
     */
    public final GitRefs GIT_REFS = io.insight.jgit.jdbc.jooq.tables.GitRefs.GIT_REFS;

    /**
     * No further instances allowed
     */
    private Test() {
        super("test", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            GitConfig.GIT_CONFIG,
            GitObjects.GIT_OBJECTS,
            GitRefs.GIT_REFS);
    }
}
