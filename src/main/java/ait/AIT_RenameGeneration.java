package ait;

public class AIT_RenameGeneration {

    AdaptiveInstructionTree tree = new AdaptiveInstructionTree();

    public AIT_RenameGeneration() {
        tree.setDescription("... Remember, code is written for humans ...");
        tree.setRefactorMechanic("Rename Method");

        Instruction i1 = new Instruction(1, "Below are INSTRUCTIONS for renaming $method in class $class of your project:\n");
        Instruction i2 = new Instruction(2, "In the current context there is no risk in renaming method $method directly");
        Instruction i3 = new Instruction(3, "Rename $method in class $class to the new name");
        Instruction i4 = new Instruction(4, "Build your project and resolved unresolved references to $method");
        Instruction i5 = new Instruction(5, "$method is not declared for the first time in $class");
        Instruction i6 = new Instruction(6, "A declaration exists in interface $interface.\nIt is a good practice to \n\t1. Mark $method deprecated in $interface \n\t2. Declare new method in $interface");
        Instruction i7 = new Instruction(7, "$method has been defined in the following superclasses: $super-list\nCheck if they need to be renamed also to preserve application behavior.\n When no risk. proceed...");
        Instruction i8 = new Instruction( 8, "To eliminate any side-effect risks, I suggest to rename $method also in $super-list\n");
        Instruction i9 = new Instruction( 9, "There are methods present in your class hierarchy with the same name (method override), but different number of parameters.\nIt is a good practice to rename also these methods to the new name you have choosen ");

        ContextDecision i1_d1 = new ContextDecision(CodeContext.CodeContextEnum.method_single_declaration, 2);
        ContextDecision i1_d2 = new ContextDecision(CodeContext.CodeContextEnum.method_multiple_declares, 5);
        ContextDecision i2_d1 = new ContextDecision(CodeContext.CodeContextEnum.always_true, 3);
        ContextDecision i3_d1 = new ContextDecision(CodeContext.CodeContextEnum.always_true, 4);
        ContextDecision i5_d1 = new ContextDecision(CodeContext.CodeContextEnum.method_defined_in_interface, 6);
        ContextDecision i5_d2 = new ContextDecision(CodeContext.CodeContextEnum.method_override, 7);
        ContextDecision i6_d1 = new ContextDecision(CodeContext.CodeContextEnum.method_override, 7);
        ContextDecision i6_d2 = new ContextDecision(CodeContext.CodeContextEnum.always_true, 3);
        ContextDecision i7_d1 = new ContextDecision(CodeContext.CodeContextEnum.always_true, 8);
        ContextDecision i8_d1 = new ContextDecision(CodeContext.CodeContextEnum.always_true, 3);
        ContextDecision i9_d1 = new ContextDecision(CodeContext.CodeContextEnum.always_true, AdaptiveInstructionTree.FINAL_NODE);
        ContextDecision i4_d1 = new ContextDecision(CodeContext.CodeContextEnum.method_overload_declaration, 9);
        ContextDecision i4_d2 = new ContextDecision(CodeContext.CodeContextEnum.always_true, AdaptiveInstructionTree.FINAL_NODE);

        i1.addDecision(i1_d1);
        i1.addDecision(i1_d2);
        i2.addDecision(i2_d1);
        i3.addDecision(i3_d1);
        i4.addDecision(i4_d1);
        i4.addDecision(i4_d2);
        i5.addDecision(i5_d1);
        i5.addDecision(i5_d2);
        i6.addDecision(i6_d1);
        i6.addDecision(i6_d2);
        i7.addDecision(i7_d1);
        i8.addDecision(i8_d1);
        i9.addDecision(i9_d1);

        tree.setFirstInstruction(i1);

        tree.addInstruction(i2);
        tree.addInstruction(i3);
        tree.addInstruction(i4);
        tree.addInstruction(i5);
        tree.addInstruction(i6);
        tree.addInstruction(i7);
        tree.addInstruction(i8);
        tree.addInstruction(i9);
    }

    public AdaptiveInstructionTree getAdaptiveInstructionTree()
    {
        return tree;
    }
}