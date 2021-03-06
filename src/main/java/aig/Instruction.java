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

package aig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement (name = "INSTRUCTION")
@XmlType(propOrder = {"instructionDescription", "decisions"})
public class Instruction {

    @XmlAttribute
    int     instructionID = -1;
    String  instructionDescription;

    List<ContextDecision> decisions = new ArrayList<>();

    public Instruction() {
    }

    public Instruction(int i, String s) {
        this.instructionDescription = s;
        this.instructionID = i;
    }

    @XmlElement(name = "TEXT")
    public void setInstructionDescription(String description) {
        this.instructionDescription = description;
    }
    public String getInstructionDescription() {
        return instructionDescription;
    }

    @XmlElement(name= "DECISION")
    public void setDecisions(List<ContextDecision> decisions) {
        this.decisions = decisions;
    }
    public List<ContextDecision> getDecisions()
    {
        return decisions;
    }

    /**
     * Adds one or more decisions that can be taken after the specific instruction
     * @param decision defines a code context based decision that leads to a new instruction
     */
    public void addDecision(ContextDecision decision)
    {
        if (decisions == null)
        {
            decisions = new ArrayList<>();
        }

        decisions.add(decision);
    }

    public boolean endNode() {
        return decisions.isEmpty();
    }
}
