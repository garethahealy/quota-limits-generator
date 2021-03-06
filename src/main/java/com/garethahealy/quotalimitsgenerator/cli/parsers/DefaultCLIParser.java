/*-
 * #%L
 * GarethHealy :: Quota Limits Generator
 * %%
 * Copyright (C) 2013 - 2018 Gareth Healy
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.garethahealy.quotalimitsgenerator.cli.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class DefaultCLIParser {

    private CommandLineParser parser;

    public DefaultCLIParser(CommandLineParser parser) {
        this.parser = parser;
    }

    public Options getOptions() {
        //Setup the options we can use on the command line
        Option instanceTypeCsv = new Option("csv", "instance-type-csv", true, "Instance type csv information");
        Option instanceType = new Option("it", "instance-type", true, "Instance type, i.e.: small, medium");
        Option qualityOfService = new Option("qos", "quality-of-service", true, "Quality of service required in project, i.e.: besteffort or burstable");
        Option nodeWorkerCount = new Option("nwc", "node-worker-count", true, "Number of work nodes in cluster, i.e.: 1");
        Option isTeamNamespace = new Option("itn", "is-team-namespace", true, "Is this for several projects or just one, i.e.: true");
        Option requestRatio = new Option("rr", "request-ratio", true, "Request ration to limit for CPU and Memory, i.e.: 3");
        Option output = new Option("o", "output", true, "Output directory, i.e.: /tmp/quotas-and-limits");

        Options options = new Options();
        options.addOption(instanceTypeCsv);
        options.addOption(instanceType);
        options.addOption(qualityOfService);
        options.addOption(nodeWorkerCount);
        options.addOption(isTeamNamespace);
        options.addOption(requestRatio);
        options.addOption(output);

        return options;
    }

    public CommandLine parse(String[] args, Options options) throws ParseException {
        if (options == null || options.getOptions() == null || options.getOptions().size() <= 0) {
            throw new ParseException("Provided options is null or empty");
        }

        if (args == null || args.length <= 0) {
            throw new ParseException("Provided args is null or empty");
        }

        return parser.parse(options, args, true);
    }

    public void displayHelp(boolean throwException) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("quota-limits-generator-cli", getOptions());

        if (throwException) {
            throw new IllegalStateException("Missing arguments");
        }
    }

    public CLIOptions getParsedOptions(CommandLine line) throws NumberFormatException, IOException, ParseException, URISyntaxException {
        String instanceTypeCsv = line.getOptionValue("instance-type-csv");
        String instanceType = line.getOptionValue("instance-type");
        String qualityOfService = line.getOptionValue("quality-of-service");
        Integer nodeWorkerCount = Integer.parseInt(line.getOptionValue("node-worker-count"));
        Boolean isTeamNamespace = Boolean.parseBoolean(line.getOptionValue("is-team-namespace"));
        Integer requestRatio = Integer.parseInt(line.getOptionValue("request-ratio"));
        URI outputPath = new URI(line.getOptionValue("output"));

        if (!"besteffort".equals(qualityOfService) && !"burstable".equals(qualityOfService)) {
            throw new ParseException("quality-of-service is not supported, expected 'besteffort' or 'burstable': " + qualityOfService);
        }

        return new CLIOptions(parseLines(instanceTypeCsv), instanceType, qualityOfService, nodeWorkerCount, isTeamNamespace, requestRatio, outputPath);
    }

    private Map<String, Pair<Integer, Integer>> parseLines(String instanceTypeCsv) throws IOException, URISyntaxException, ParseException {
        InputStreamReader inputStreamReader;
        if (instanceTypeCsv.equalsIgnoreCase("classpath")) {
            inputStreamReader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("instancetypes.csv"), Charset.forName("UTF-8"));
        } else {
            URI uri = new URI(instanceTypeCsv);
            inputStreamReader = new InputStreamReader(new FileInputStream(new File(uri)), Charset.forName("UTF-8"));
        }

        CSVParser parser = null;
        List<CSVRecord> lines = null;
        try {
            parser = CSVFormat.DEFAULT.parse(new BufferedReader(inputStreamReader));
            lines = parser.getRecords();
        } finally {
            inputStreamReader.close();

            if (parser != null) {
                parser.close();
            }
        }

        if (lines == null || lines.size() <= 0) {
            throw new ParseException("instance-type-csv data is empty");
        }

        Map<String, Pair<Integer, Integer>> linesMap = new HashMap<String, Pair<Integer, Integer>>();
        for (CSVRecord current : lines) {
            linesMap.put(current.get(1), new ImmutablePair<Integer, Integer>(Integer.parseInt(current.get(2)), Integer.parseInt(current.get(3))));
        }

        return linesMap;
    }
}
