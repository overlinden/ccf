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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Oliver Verlinden
 */
public class CcfTest {
    
    private Ccf app;
    
    @Before
    public void init() {
        app = new Ccf();
    }
    
    @Test
    public void correctNoOfColsAreKept() {
        String input = "abcdef\r\n123;456\r\n";
        InputStream is = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        app.filter(is,bos,2);
        
        String output = bos.toString();
        assertThat(output, containsString("123;456"));
    }
    
    @Test
    public void incorrectNoOfColsAreDropped() {
        String input = "abcdef\r\n123;456\r\n";
        InputStream is = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        app.filter(is,bos,2);
        
        String output = bos.toString();
        assertThat(output, not(containsString("abcdef")));
    }

    @Test
    public void linesAreSeparatedWithCorrectLineEndings() {
        String input = "abcdef\r\n";
        InputStream is = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        app.filter(is,bos,1);
        
        String output = bos.toString();
        assertThat(output, anyOf(is("abcdef\r\n"), is("abcdef\n")));
    }
    
    @Test
    public void emptyTrailingFieldsArePreserved() {
        String input = "abcdef;;;\r\n";
        InputStream is = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        app.filter(is,bos,4);
        
        String output = bos.toString();
        assertThat(output, is(input));
    }
}
