/**
 *  This file is part of RefactorGuidance project. Which explores possibilities to generate context based
 *  instructions on how to refactor a piece of Java code. This applied in an education setting (bachelor SE students)
 *
 *      Copyright (C) 2018, Patrick de Beer, p.debeer@fontys.nl
 *
 *          This program is free software: you can redistribute it and/or modify
 *          it under the terms of the GNU General Public License as published by
 *          the Free Software Foundation, either version 3 of the License, or
 *          (at your option) any later version.
 *
 *          This program is distributed in the hope that it will be useful,
 *          but WITHOUT ANY WARRANTY; without even the implied warranty of
 *          MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *          GNU General Public License for more details.
 *
 *          You should have received a copy of the GNU General Public License
 *          along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package analysis.dataflow;

import java.util.ArrayList;
import java.util.List;

public class VariableFacts {

        public boolean write;
        public List<Loc> written_at = new ArrayList<>();

        public boolean read;
        public List<Loc> read_at = new ArrayList<>();

        public boolean cond_write;

        public boolean live; // A variable is written which before only has been read in the section

        public boolean areAllFactsFalse() { return !write && !read && !cond_write && !live;}

        public class Loc {
                public int lineNumber;
                public int column;
                public int statementIndex;

                int getLineNumber(){ return lineNumber;}
        }
}
