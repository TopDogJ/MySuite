package com.mysuite.mytrade.archive.service;

import com.mysuite.commons.exception.entity.EntityNotFoundException;
import com.mysuite.mytrade.api.entity.bean.country.Country;
import com.mysuite.mytrade.api.entity.bean.exchange.Exchange;
import com.mysuite.mytrade.api.entity.bean.exchange.ExchangeType;
import com.mysuite.mytrade.api.entity.bean.security.Status;
import com.mysuite.mytrade.api.entity.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Created by jianl on 16/04/2017.
 */
@Service
public class SystemConfigurationServiceImpl implements SystemConfigurationService {

    @Autowired
    @Qualifier("countryEntityRepository")
    private EntityRepository<Country> countryEntityRepository;
    @Autowired
    @Qualifier("exchangeEntityRepository")
    private EntityRepository<Exchange> exchangeEntityRepository;
    @Autowired
    @Qualifier("exchangeTypeEntityRepository")
    private EntityRepository<ExchangeType> exchangeTypeEntityRepository;
    @Autowired
    @Qualifier("statusEntityRepository")
    private EntityRepository<Status> statusEntityRepository;
//    @Autowired
//    private EntityRepository<SecurityType> securityTypeRepository;
//    @Autowired
//    private EntityRepository<TransactionType> transactionTypeRepository;
//    @Autowired
//    private EntityRepository<TransactionStatus> transactionStatusRepository;
//    @Autowired
//    private EntityRepository<QuoteType> quoteTypeRepository;
//    @Autowired
//    private EntityRepository<HolidayType> holidayTypeRepository;
//    @Autowired
//    private CalendarManager calendarManager;
//    @Autowired
//    private EntityRepository<ActionLevel> actionLevelRepository;
//    @Autowired
//    private EntityRepository<VolumePublication> volumePublicationRepository;
//    @Autowired
//    private EntityRepository<PublicationStatus> publicationStatusRepository;
//    @Autowired
//    private EntityRepository<PublishFrequency> publishFrequencyRepository;

//    @Transactional
//    public void initialisePublishStatus(){
//        PublicationStatus active = new PublicationStatus();
//        active.setName("Active");
//        active.setCode("A");
//
//        this.publicationStatusRepository.save(active);
//        PublicationStatus inactive = new PublicationStatus();
//        inactive.setName("In-Active");
//        inactive.setCode("I");
//        this.publicationStatusRepository.save(inactive);
//    }
//
//    @Transactional
//    public void initialisePublishFrequencies(){
//        PublishFrequency publishFrequency = new PublishFrequency();
//        publishFrequency.setName("Daily");
//        publishFrequency.setCode("D");
//        this.publishFrequencyRepository.save(publishFrequency);
//    }
//
//    @Transactional
//    public void initialiseVolumePublications() throws EntityNotFoundException {
//        VolumePublication big5 = new VolumePublication();
//        big5.setName("Volume-Big-Five");
//        big5.setCode("V5");
//        big5.setTopicName("volume.big.five");
//        big5.setBasedOnNumberOfDays(5);
//        big5.setPublicationStatus(this.publicationStatusRepository.findByDuplicate("A"));
//        big5.setPublishFrequency(this.publishFrequencyRepository.findByDuplicate("D"));
//        this.volumePublicationRepository.save(big5);
//
//        VolumePublication big10 = new VolumePublication();
//        big10.setName("Volume-Big-Ten");
//        big10.setCode("V10");
//        big10.setTopicName("volume.big.ten");
//        big10.setBasedOnNumberOfDays(10);
//        big10.setPublicationStatus(this.publicationStatusRepository.findByDuplicate("A"));
//        big10.setPublishFrequency(this.publishFrequencyRepository.findByDuplicate("D"));
//        this.volumePublicationRepository.save(big10);
//    }

    @Transactional
    public void initialiseCountries() {
        Country china = new Country();
        china.setName("中华人民共和国");
        china.setCode("CN");
        this.countryEntityRepository.save(china);
    }

    @Transactional
    public void initialiseExchanges() throws EntityNotFoundException {
        Exchange sh = new Exchange();
        sh.setName("上海证交所");
        sh.setCode("sh");
        sh.setCountry(this.countryEntityRepository.findByDuplicate(Arrays.asList("CN")));
        this.exchangeEntityRepository.save(sh);

        Exchange sz = new Exchange();
        sz.setName("深圳证交所");
        sz.setCode("sz");
        sz.setCountry(this.countryEntityRepository.findByDuplicate(Arrays.asList("CN")));
        this.exchangeEntityRepository.save(sz);
    }

    @Transactional
    public void initialiseExchangeTypes() throws EntityNotFoundException {
        ExchangeType sha = new ExchangeType();
        sha.setName("A股");
        sha.setCode("A");
        sha.setExchange(this.exchangeEntityRepository.findByDuplicateForReference(Arrays.asList("sh")));
        this.exchangeTypeEntityRepository.save(sha);

        ExchangeType shb = new ExchangeType();
        shb.setName("B股");
        shb.setCode("B");
        shb.setExchange(this.exchangeEntityRepository.findByDuplicateForReference(Arrays.asList("sh")));
        this.exchangeTypeEntityRepository.save(shb);

        ExchangeType sza = new ExchangeType();
        sza.setName("A股");
        sza.setCode("A");
        sza.setExchange(this.exchangeEntityRepository.findByDuplicateForReference(Arrays.asList("sz")));
        this.exchangeTypeEntityRepository.save(sza);

        ExchangeType szb = new ExchangeType();
        szb.setName("B股");
        szb.setCode("B");
        szb.setExchange(this.exchangeEntityRepository.findByDuplicateForReference(Arrays.asList("sz")));
        this.exchangeTypeEntityRepository.save(szb);
    }

    @Override
    @Transactional
    public void initialiseSecurityStatus() throws EntityNotFoundException {
        Status newStatus = new Status();
        newStatus.setName("New");
        newStatus.setCode("N");
        this.statusEntityRepository.save(newStatus);

        Status learning = new Status();
        learning.setName("Standard-Learn");
        learning.setCode("SL");
        this.statusEntityRepository.save(learning);

        Status deepLearning = new Status();
        deepLearning.setName("Deep-Learn");
        deepLearning.setCode("DL");
        this.statusEntityRepository.save(deepLearning);

        Status archive = new Status();
        archive.setName("Standard-Archive");
        archive.setCode("SA");
        this.statusEntityRepository.save(archive);

        Status deepAchive = new Status();
        deepAchive.setName("Deep-Archive");
        deepAchive.setCode("DA");
        this.statusEntityRepository.save(deepAchive);

        Status archiving = new Status();
        archiving.setName("Archiving");
        archiving.setCode("AN");
        this.statusEntityRepository.save(archiving);


        Status archived = new Status();
        archived.setName("Archived");
        archived.setCode("AD");
        this.statusEntityRepository.save(archived);

        Status track = new Status();
        track.setName("Standard-Track");
        track.setCode("T");
        this.statusEntityRepository.save(track);

        Status deepTrack = new Status();
        deepTrack.setName("Deep-Track");
        deepTrack.setCode("DT");
        this.statusEntityRepository.save(deepTrack);

        Status excludeDividentAndRight = new Status();
        excludeDividentAndRight.setName("Exclude Divident and Right");
        excludeDividentAndRight.setCode("DR");
        this.statusEntityRepository.save(excludeDividentAndRight);

        Status excludeRight = new Status();
        excludeRight.setName("Exclude Right");
        excludeRight.setCode("XR");
        this.statusEntityRepository.save(excludeRight);

        Status excludeDivident = new Status();
        excludeDivident.setName("Exclude Divident");
        excludeDivident.setCode("XD");
        this.statusEntityRepository.save(excludeDivident);

        Status specialTreatment = new Status();
        specialTreatment.setName("Special Treatment");
        specialTreatment.setCode("ST");
        this.statusEntityRepository.save(specialTreatment);
    }

//    @Transactional
//    public void initialiseTransactionTypes() {
//        TransactionType buy = new TransactionType();
//        buy.setName("买入");
//        buy.setCode("B");
//        this.transactionTypeRepository.save(buy);
//
//        TransactionType sell = new TransactionType();
//        sell.setName("卖出");
//        sell.setCode("S");
//        this.transactionTypeRepository.save(sell);
//
//        TransactionType medium = new TransactionType();
//        medium.setName("介入");
//        medium.setCode("M");
//        this.transactionTypeRepository.save(medium);
//
//        TransactionType buy1 = new TransactionType();
//        buy1.setName("委买一");
//        buy1.setCode("B1");
//        this.transactionTypeRepository.save(buy1);
//
//        TransactionType buy2 = new TransactionType();
//        buy2.setName("委买二");
//        buy2.setCode("B2");
//        this.transactionTypeRepository.save(buy2);
//
//        TransactionType buy3 = new TransactionType();
//        buy3.setName("委买三");
//        buy3.setCode("B3");
//        this.transactionTypeRepository.save(buy3);
//
//        TransactionType buy4 = new TransactionType();
//        buy4.setName("委买四");
//        buy4.setCode("B4");
//        this.transactionTypeRepository.save(buy4);
//
//        TransactionType buy5 = new TransactionType();
//        buy5.setName("委买五");
//        buy5.setCode("B5");
//        this.transactionTypeRepository.save(buy5);
//
//        TransactionType sell1 = new TransactionType();
//        sell1.setName("委卖一");
//        sell1.setCode("S1");
//        this.transactionTypeRepository.save(sell1);
//
//        TransactionType sell2 = new TransactionType();
//        sell2.setName("委卖二");
//        sell2.setCode("S2");
//        this.transactionTypeRepository.save(sell2);
//
//        TransactionType sell3 = new TransactionType();
//        sell3.setName("委卖三");
//        sell3.setCode("S3");
//        this.transactionTypeRepository.save(sell3);
//
//        TransactionType sell4 = new TransactionType();
//        sell4.setName("委卖四");
//        sell4.setCode("S4");
//        this.transactionTypeRepository.save(sell4);
//
//        TransactionType sell5 = new TransactionType();
//        sell5.setName("委卖五");
//        sell5.setCode("S5");
//        this.transactionTypeRepository.save(sell5);
//    }
//
//    @Override
//    @Transactional
//    public void initialiseTransactionStatuses() {
//        TransactionStatus lodged = new TransactionStatus();
//        lodged.setName("委托");
//        lodged.setCode("LG");
//        this.transactionStatusRepository.save(lodged);
//
//        TransactionStatus confirmed = new TransactionStatus();
//        lodged.setName("确认");
//        lodged.setCode("CN");
//        this.transactionStatusRepository.save(confirmed);
//    }
//
//    @Transactional
//    public void initialiseSecurityTypes() {
//        SecurityType securityType = new SecurityType();
//        securityType.setName("股票");
//        securityType.setCode("EQ");
//        this.securityTypeRepository.save(securityType);
//    }
//
//    @Override
//    @Transactional
//    public void initialiseActionLevel(){
//        ActionLevel standard = new ActionLevel();
//        standard.setName("Standard");
//        standard.setCode("S");
//        standard.setInDays(24);
//        this.actionLevelRepository.save(standard);
//
//        ActionLevel deep = new ActionLevel();
//        deep.setName("Deep");
//        deep.setCode("D");
//        deep.setInDays(-1);
//        this.actionLevelRepository.save(deep);
//    }
//
//    @Override
//    @Transactional
//    public void initialiseSecurityStatus() throws EntityNotFoundException {
//
//        SecurityStatus newStatus = new SecurityStatus();
//        newStatus.setName("New");
//        newStatus.setCode("N");
//        this.securityStatusRepository.save(newStatus);
//
//        SecurityStatus learning = new SecurityStatus();
//        learning.setName("Standard-Learn");
//        learning.setCode("SL");
//        learning.setActionLevel(this.actionLevelRepository.findByDuplicate("S"));
//        this.securityStatusRepository.save(learning);
//
//        SecurityStatus deepLearning = new SecurityStatus();
//        deepLearning.setName("Deep-Learn");
//        deepLearning.setCode("DL");
//        deepLearning.setActionLevel(this.actionLevelRepository.findByDuplicate("D"));
//        this.securityStatusRepository.save(deepLearning);
//
//        SecurityStatus archive = new SecurityStatus();
//        archive.setName("Standard-Archive");
//        archive.setCode("SA");
//        archive.setActionLevel(this.actionLevelRepository.findByDuplicate("S"));
//        this.securityStatusRepository.save(archive);
//
//        SecurityStatus deepAchive = new SecurityStatus();
//        deepAchive.setName("Deep-Archive");
//        deepAchive.setCode("DA");
//        deepAchive.setActionLevel(this.actionLevelRepository.findByDuplicate("D"));
//        this.securityStatusRepository.save(deepAchive);
//
//        SecurityStatus archiving = new SecurityStatus();
//        archiving.setName("Archiving");
//        archiving.setCode("AN");
//        this.securityStatusRepository.save(archiving);
//
//
//        SecurityStatus archived = new SecurityStatus();
//        archived.setName("Archived");
//        archived.setCode("AD");
//        this.securityStatusRepository.save(archived);
//
//        SecurityStatus track = new SecurityStatus();
//        track.setName("Standard-Track");
//        track.setCode("ST");
//        this.securityStatusRepository.save(track);
//
//        SecurityStatus deepTrack = new SecurityStatus();
//        deepTrack.setName("Deep-Track");
//        deepTrack.setCode("DT");
//        this.securityStatusRepository.save(deepTrack);
//    }
//
//    @Override
//    @Transactional
//    public void initialiseQuoteType() {
//        QuoteType daily = new QuoteType();
//        daily.setName("Daily");
//        daily.setCode("D");
//        this.quoteTypeRepository.save(daily);
//
//        QuoteType rt = new QuoteType();
//        rt.setName("Real Time");
//        rt.setCode("RT");
//        this.quoteTypeRepository.save(rt);
//    }
//
//    @Transactional
//    public void initialiseHolidayTypes(){
//        HolidayType weekendType = new HolidayType();
//        weekendType.setName("周末");
//        weekendType.setCode("WE");
//        this.holidayTypeRepository.save(weekendType);
//
//        HolidayType byLawType = new HolidayType();
//        byLawType.setName("法定");
//        byLawType.setCode("BL");
//        this.holidayTypeRepository.save(byLawType);
//    }
//
//    @Transactional
//    public void initialiseHolidays() throws EntityNotFoundException, IOException {
//        List<Holiday> weekends = this.loadWeekends();
//        weekends.forEach(holiday -> this.calendarManager.save(holiday));
//        List<Holiday> holidays = this.loadByLawHolidaysFromFile();
//        holidays.forEach(holiday -> this.calendarManager.save(holiday));
//    }
//
//
//    private List<Holiday> loadWeekends() throws EntityNotFoundException {
//        Date start = DateTimeFormatHelper.getDateFromDateString("2000-01-01");
//        Date end = DateTimeFormatHelper.getDateFromDateString("2020-12-31");
//
//        Calendar startCalendar = Calendar.getInstance();
//        startCalendar.setTime(start);
//        Long currentDay = startCalendar.getTimeInMillis();
//        Long aDay = 1000 * 60 * 60 * 24l;
//        Calendar endCalendar = Calendar.getInstance();
//        endCalendar.setTime(end);
//        Long endDay = endCalendar.getTimeInMillis();
//        List<Holiday> weekendList = new ArrayList<>();
//        while (currentDay <= endDay){
//            Date current = new Date(currentDay);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(current);
//            if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
//                Holiday holiday = new Holiday();
//                holiday.setDate(current);
//                holiday.setHolidayType(this.holidayTypeRepository.findByDuplicate("WE"));
//                weekendList.add(holiday);
//            }
//            currentDay += aDay;
//        }
//        return weekendList;
//    }
//
//    private List<Holiday> loadByLawHolidaysFromFile() throws EntityNotFoundException, IOException {
//        List<Holiday> holidays = new ArrayList<>();
//        File file = new File("C:/Workspace/mytrade-file-repo/holiday.txt");
//        BufferedReader reader = new BufferedReader( new FileReader(file));
//        while (reader.readLine()!=null){
//            String dateString = reader.readLine();
//            Date date = DateTimeFormatHelper.getDateFromDateString(dateString);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            if(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
//                Holiday holiday = new Holiday();
//                holiday.setDate(date);
//                holiday.setCountry(this.countryRepository.findByDuplicate("CN"));
//                holiday.setHolidayType(this.holidayTypeRepository.findByDuplicate("BL"));
//                holidays.add(holiday);
//            }else {
//                System.out.println(dateString + "is weekend.");
//            }
//        }
//        reader.close();
//        return  holidays;
//    }
}
