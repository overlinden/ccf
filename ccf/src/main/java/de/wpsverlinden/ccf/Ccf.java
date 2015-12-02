/*
 * ccf - csv column filter
 * Copyright (C) 2015 Oliver Verlinden (http://wps-verlinden.de)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package de.wpsverlinden.ccf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Oliver Verlinden
 */
public class Ccf {

    public static void main(String[] args) {
        Ccf app = new Ccf();
        app.run(args);
    }

    private void run(String[] args) {
        try {
            int numOfColumns = Integer.parseInt(args[0]);
            filter(System.in, System.out, numOfColumns);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage 'java -jar ccf.jar no_of_columns'");
            System.exit(0);
        }
    }

    public void filter(InputStream is, OutputStream os, int numOfColumns) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(os), true);
        Pattern regEx = Pattern.compile("^(?:[^;]*+;){" + (numOfColumns - 1) + "}[^;]*+$");
        Matcher matcher = regEx.matcher(""); //create matcher with dummy string
        in.lines()
                .filter(l -> matcher.reset(l).matches())
                .forEachOrdered(out::println);
    }
}
