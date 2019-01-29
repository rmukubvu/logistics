package za.charurama.logistics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.constants.SequenceTypes;
import za.charurama.logistics.models.AutoNumber;
import za.charurama.logistics.repository.AutoNumberGeneratorRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AutoNumberService {

    @Autowired
    private AutoNumberGeneratorRepository autoNumberGeneratorRepository;

    public long getWayBillSequence(){
        return getNextVal(SequenceTypes.SEQ_WAYBILL,SequenceTypes.SEQ_WAYBILL_START_INDEX);
    }

    public String getManifestReference() {
        long nextVal = getNextVal(SequenceTypes.SEQ_MANIFEST_REFERENCE, SequenceTypes.SEQ_MANIFEST_REFERENCE_START_INDEX);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        String yearMonth = LocalDate.now().format(formatter);
        return String.format("ZC%s%d", yearMonth, nextVal);
    }

    private long getNextVal(String sequenceName,int startSequnce){
        AutoNumber autoNumber = autoNumberGeneratorRepository.findFirstBySequenceNameEquals(sequenceName);
        if (autoNumber == null){
            autoNumber = new AutoNumber();
            autoNumber.setSequenceName(sequenceName);
            autoNumber.setIncrementBy(1);
            autoNumber.setSequence(startSequnce);
        }else {
            long sequence = autoNumber.getSequence() + autoNumber.getIncrementBy();
            autoNumber.setSequence(sequence);
        }
        autoNumberGeneratorRepository.save(autoNumber);
        return autoNumber.getSequence();
    }



}
