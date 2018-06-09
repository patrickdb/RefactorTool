package aig;

public class CodeContext {

    public enum contextAction {
        listSameName
    }

    public enum CodeContextEnum {

        // Rename refactoring defined context
        MethodSingleDeclaration,
        MethodMultipleDeclarations,
        MethodNoneInterfaceDeclaration,
        MethodInterfaceDeclaration,
        MethodOverload,
        MethodNoneOverload,
        MethodOverride,
        MethodNoneOverride,
        MethodOverrideNoAnnotation,
        MethodNoneOverrideNoAnnotation,

        // Extract method
        MethodExtractNoneLocalDependencies,
        MethodExtractSingleArgument,
        MethodExtractSingleResult,
        MethodExtractMultiArgument,
        MethodExtractMultiResult,
        MethodExtractNameHiding, // variable name is binded on different scopes
        MethodExtractNoNameHiding,
        MethodExtractNoneArguments,
        MethodExtractNoneResults,
        intramethod_extract_flow_break,
        intramethod_extract_flow_return

        , always_true     // used to link multiple actions together, where no decision is required
    }
}
