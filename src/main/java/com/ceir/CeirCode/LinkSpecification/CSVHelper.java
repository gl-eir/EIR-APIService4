package com.ceir.CeirCode.LinkSpecification;

import com.ceir.CeirCode.model.app.LinkDetails;

import java.io.PrintWriter;
import java.util.List;

public class CSVHelper {

    public static void writeLinksToCsv(List<LinkDetails> links, PrintWriter writer) {
        writer.println("linkId,createdOn,modifiedOn,status");
        for (LinkDetails link : links) {
            writer.println(link.getLinkId() + "," +
                    link.getCreatedOn() + "," +
                    link.getModifiedOn() + "," +
                    link.getStatus());
        }
    }
}

