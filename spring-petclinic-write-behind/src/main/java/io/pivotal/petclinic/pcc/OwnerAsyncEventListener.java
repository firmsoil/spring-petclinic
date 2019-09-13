package io.pivotal.petclinic.pcc;

import lombok.extern.java.Log;
import org.apache.geode.cache.Operation;
import org.apache.geode.cache.asyncqueue.AsyncEvent;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;

import java.util.List;

@Log
public class OwnerAsyncEventListener implements AsyncEventListener {

    public boolean processEvents(@SuppressWarnings("rawtypes") List<AsyncEvent> list) {

        log.info(String.format("Size of List<GatewayEvent> = %s", list.size()));
//        List<JdbcBatch> newEntries = new ArrayList<JdbcBatch>();
//
//        List<JdbcBatch> updatedEntries = new ArrayList<JdbcBatch>();
//        List<String> destroyedEntries = new ArrayList<String>();
        int possibleDuplicates = 0;

        for (@SuppressWarnings("rawtypes") AsyncEvent ge : list) {

            if (ge.getPossibleDuplicate())
                possibleDuplicates++;

            if (ge.getOperation().equals(Operation.UPDATE)) {
                log.info("Owner updated " + ge.getSerializedValue().toString());
//                updatedEntries.add((JdbcBatch) ge.getDeserializedValue());
            } else if (ge.getOperation().equals(Operation.CREATE)) {
                log.info("Owner created " + ge.getSerializedValue().toString());
//                newEntries.add((JdbcBatch) ge.getDeserializedValue());
            } else if (ge.getOperation().equals(Operation.DESTROY)) {
                log.info("Owner destroyed " + ge.getSerializedValue().toString());
//                destroyedEntries.add(ge.getKey().toString());
            }
        }
        return true;
    }
}
