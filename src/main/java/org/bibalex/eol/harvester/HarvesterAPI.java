package org.bibalex.eol.harvester;

import org.apache.commons.io.FilenameUtils;
import org.bibalex.eol.handler.ExtensionsHandler;
import org.bibalex.eol.handler.MetaHandler;
import org.bibalex.eol.parser.DwcaParser;
import org.bibalex.eol.parser.handlers.PropertiesHandler;
import org.bibalex.eol.validator.DwcaValidator;
import org.gbif.dwca.io.Archive;
import org.gbif.dwca.io.ArchiveFactory;
import org.gbif.dwca.io.ArchiveFile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;

@Service
public class HarvesterAPI {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean preProcessing(String path, int resourceID, boolean newResource){
        try {
            MetaHandler metaHandler = new MetaHandler();
            ExtensionsHandler extensionsHandler = new ExtensionsHandler();
            File myArchiveFile = new File(path);
            File extractToFolder = new File(FilenameUtils.removeExtension(path) + ".out");
            Archive dwcArchive = null;
            try {
                dwcArchive = ArchiveFactory.openArchive(myArchiveFile, extractToFolder);
            } catch (Exception e) {
                System.out.println("folder need to editing to be readable by library");
                metaHandler.adjustMetaFileToBeReadableByLibrary(extractToFolder.getPath());
                dwcArchive = ArchiveFactory.openArchive(extractToFolder);
            }
            for(ArchiveFile extension : dwcArchive.getExtensions()){
                extensionsHandler.duplicateCompositeKey(extension);
            }

            return callValidation(dwcArchive, metaHandler, newResource, resourceID, dwcArchive.getLocation().getPath());
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean callValidation(Archive dwcArchive, MetaHandler metaHandler, boolean newResource, int resourceID, String path){
        try {
//
            DwcaValidator validator = new DwcaValidator("configs.properties");

            System.out.println("call validationnnnnnnnnnnnnn");
            validator.validateArchive(dwcArchive.getLocation().getPath(), dwcArchive);
            String validArchivePath= FilenameUtils.removeExtension(path)+".out_valid";
            metaHandler.addGeneratedAutoId(validArchivePath);

            boolean done = callParser(validArchivePath, resourceID, newResource, entityManager);
            return done;
        } /*catch (IOException e) {
            e.printStackTrace();
//            System.out.println("exceptionnnnnnnnnnnnnnnnnnnnn");
            return false;
        } */catch (Exception e) {
//            System.out.println("exceptionnnnnnnnnnnnnnnnnnnnn");
            e.printStackTrace();
            return false;
        }

    }

    private boolean callParser(String path, int resourceID, boolean newResource, EntityManager entityManager){
        Archive dwcArchive = null;
        try {
            //for testing
//            File myArchiveFile = new File(path);
//            File extractToFolder = new File(FilenameUtils.removeExtension(path) + ".out");
//            dwcArchive = ArchiveFactory.openArchive(myArchiveFile, extractToFolder);

            // for production
            PropertiesHandler.initializeProperties();
            dwcArchive = ArchiveFactory.openArchive(new File(path));

            //general
            DwcaParser dwcaP = new DwcaParser(dwcArchive, newResource, entityManager, resourceID);
            dwcaP.prepareNodesRecord();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
