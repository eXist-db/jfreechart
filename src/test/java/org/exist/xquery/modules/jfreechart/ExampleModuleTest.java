package org.exist.xquery.modules.jfreechart;


import org.exist.test.ExistEmbeddedServer;
import org.junit.ClassRule;

public class ExampleModuleTest {

    @ClassRule
    public static ExistEmbeddedServer existEmbeddedServer = new ExistEmbeddedServer(false, true);




//    private Sequence executeQuery(final String xquery) throws EXistException, PermissionDeniedException, XPathException {
//        final BrokerPool pool = existEmbeddedServer.getBrokerPool();
//        final XQuery xqueryService = pool.getXQueryService();
//
//        try(final DBBroker broker = pool.get(Optional.of(pool.getSecurityManager().getSystemSubject()))) {
//            return xqueryService.execute(broker, xquery, null);
//        }
//    }
}
