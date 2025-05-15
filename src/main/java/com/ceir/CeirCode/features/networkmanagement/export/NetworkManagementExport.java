package com.ceir.CeirCode.features.networkmanagement.export;

import com.ceir.CeirCode.configuration.PropertiesReader;
import com.ceir.CeirCode.exceptions.ResourceServicesException;
import com.ceir.CeirCode.features.networkmanagement.csv_file_model.NetworkManagementFileModel;
import com.ceir.CeirCode.features.networkmanagement.paging.NetworkManagementPaging;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.NetworkEntity;
import com.ceir.CeirCode.model.app.SystemConfigurationDb;
import com.ceir.CeirCode.service.ConfigurationManagementServiceImpl;
import com.ceir.CeirCode.util.CustomMappingStrategy;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class NetworkManagementExport { private final Logger logger = LogManager.getLogger(this.getClass());
    private ConfigurationManagementServiceImpl configurationManagementServiceImpl;
    private NetworkManagementPaging networkManagementPaging;

    public NetworkManagementExport(ConfigurationManagementServiceImpl configurationManagementServiceImpl, NetworkManagementPaging networkManagementPaging) {
        this.configurationManagementServiceImpl = configurationManagementServiceImpl;
        this.networkManagementPaging = networkManagementPaging;
    }

    @Autowired
    private PropertiesReader propertiesReader;

    public FileDetails export(NetworkEntity networkEntity, String subFeature) {
        String fileName = null;
        Writer writer = null;
        Integer pageNo = 0;
        Integer pageSize = Integer.valueOf(configurationManagementServiceImpl.findByTag("file.max-file-record").getValue());

        NetworkManagementFileModel fileModel = null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        SystemConfigurationDb filepath = configurationManagementServiceImpl.findByTag("file.download-dir");
        SystemConfigurationDb link = configurationManagementServiceImpl.findByTag("file.download-link");
        logger.info("File Path :  [" + filepath + "] & Link : [" + link + "]");

        StatefulBeanToCsvBuilder<NetworkManagementFileModel> builder = null;
        StatefulBeanToCsv<NetworkManagementFileModel> csvWriter = null;
        List<NetworkManagementFileModel> fileRecords = null;
        CustomMappingStrategy<NetworkManagementFileModel> mappingStrategy = new CustomMappingStrategy<>();

        try {
            List<NetworkEntity> list = networkManagementPaging.findAll(networkEntity, pageNo, pageSize).getContent();
            fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_" + subFeature + ".csv";
            writer = Files.newBufferedWriter(Paths.get(filepath.getValue() + fileName));
            mappingStrategy.setType(NetworkManagementFileModel.class);

            builder = new StatefulBeanToCsvBuilder<>(writer);
            csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',').withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
            if (list.size() > 0) {
                fileRecords = new ArrayList<NetworkManagementFileModel>();
                for (NetworkEntity data : list) {
                    fileModel = new NetworkManagementFileModel()
                            .setMnoName(data.getMnoName())
                            .setNeName(data.getNeName())
                            .setNeType(data.getNeType())
                            .setGtAddress(data.getGtAddress())
                            .setHostname(data.getHostname());
                    fileRecords.add(fileModel);
                }
                logger.info("Exported data : [" + fileRecords + "]");
                csvWriter.write(fileRecords);
            } else {
                csvWriter.write(new NetworkManagementFileModel());
            }
            logger.info("fileName [" + fileName + "] filePath [" + filepath + "] download link [" + link.getValue() + "]");

            FileDetails fileDetails = new FileDetails(fileName, filepath.getValue(), link.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + fileName);
            logger.info("export file Details [" + fileDetails + "]");
            return fileDetails;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        } finally {
            try {

                if (writer != null) writer.close();
            } catch (IOException e) {
            }
        }
    }
}
