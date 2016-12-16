/*-
 * #%L
 * GarethHealy :: Quota Limits Generator
 * %%
 * Copyright (C) 2013 - 2016 Gareth Healy
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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class DefaultCLIParser {

    private CommandLineParser parser;

    public DefaultCLIParser(CommandLineParser parser) {
        this.parser = parser;
    }

    public Options getOptions() {
        //Setup the options we can use on the command line
        Option dbDriverOption = new Option("it", "instance-type", true,
                                           "Instance type, i.e.: small, medium");

        Options options = new Options();
        options.addOption(dbDriverOption);

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

    public Model getModel(CommandLine line) {
        String instance = line.getOptionValue("instance-type");

        return new Model(instance);
    }
}
