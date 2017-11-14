package analysis;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.symbolsolver.javaparser.Navigator;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.model.declarations.ClassDeclaration;
import com.github.javaparser.symbolsolver.model.declarations.ReferenceTypeDeclaration;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.symbolsolver.model.typesystem.ReferenceType;
import helpers.Checker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ClassMethodFinder {

    private CompilationUnit _cu;
    private String _qname;
    private TypeSolver _symbolSolver;

    public ClassMethodFinder(CompilationUnit cu, String qualifiedName) {
        _cu = cu;
        _qname = qualifiedName;

        _symbolSolver = new CombinedTypeSolver(
                new ReflectionTypeSolver(),
                new JavaParserTypeSolver(new File("SymbolSolver/src/main/java/"))
        );
    }

    private boolean isIgnoredPackage(ReferenceTypeDeclaration rtd)
    {
        boolean ignoredPackage = false;

       if (rtd.hasName() && rtd.getPackageName().equalsIgnoreCase("java.lang") )
       {
           ignoredPackage = true;
       }

       return ignoredPackage;
    }

    public List<String> getAllDefinedMethods() {

        List<String> allDefinedMethods = new ArrayList<String>();

        // Find in the AST the class declaration of the provided class in the Ctor
        List<MethodDeclaration> lmd = getMethodDeclarations();

        lmd.forEach (method -> {
            String formattedOut = String.format(" %s %s : range %s", method.getType(), method.getSignature(), method.getRange().toString());
            //System.out.println(formattedOut);
            allDefinedMethods.add(formattedOut);
            });

        ClassOrInterfaceDeclaration class4Analysis = Navigator.demandClass(_cu, _qname);
        ReferenceTypeDeclaration rtd = JavaParserFacade.get(_symbolSolver).getTypeDeclaration(class4Analysis);

         //simple test to find all declared methods in local class and all of its inherited classes
        List<ReferenceType> rt = rtd.getAllAncestors();
        rt.forEach( ancestor ->
        {
            ReferenceTypeDeclaration rtd_ancestor = ancestor.getTypeDeclaration();

            if (!isIgnoredPackage(rtd_ancestor)) {
                rtd_ancestor.getDeclaredMethods().forEach(m ->
                {
                   // System.out.println(String.format("A:  %s", m.getQualifiedSignature()));
                   // System.out.println(String.format("declared in:  %s", m.declaringType().getName()));
                   // System.out.println(String.format("is interface? %s", m.declaringType().isInterface()?"yes": "no"));
                });
            }
        });

        if (!isIgnoredPackage(rtd)) {
            rtd.getAllMethods().forEach(m ->
            {
                if (!m.getQualifiedSignature().contains("java.lang")) {
                   // System.out.println(String.format("  %s", m.getQualifiedSignature()));
                   // System.out.println(String.format("declared in:  %s", m.declaringType().getName()));
                }
            });
        }

         System.out.println();

        return allDefinedMethods;
    }

    private List<MethodDeclaration> getMethodDeclarations() {

        ClassOrInterfaceDeclaration class4Analysis = Navigator.demandClassOrInterface(_cu, _qname);
        return class4Analysis.getMethods();
    }

    /**
     * Tells if a file line location lies within scope of a method
     *
     * @param location Line number in .java file
     * @return true if line number lies in method scope
     */
    public boolean isLocationInMethod(int location) {

        boolean locationInMethodDefinition = false;

        // Find in the AST the class declaration of the provided class in the Ctor
        List<MethodDeclaration> lmd = getMethodDeclarations();

        for (MethodDeclaration method : lmd)
        {
            if( Checker.inRangeInclusive(method.getRange().get().begin.line, method.getRange().get().end.line, location))
                locationInMethodDefinition = true;
        }

        return locationInMethodDefinition;
    }

    /**
     * Determine in which method  a specific location in a .java file is located
     *
     * @param location  Line number in .java file
     * @return Method name when location is inside a method, otherwise empty string
     */
    public String getMethodNameForLocation(int location) {
        String methodName = new String();

        if(isLocationInMethod(location)) {
            // Find in the AST the class declaration of the provided class in the Ctor
            List<MethodDeclaration> lmd = getMethodDeclarations();

            for (MethodDeclaration method : lmd) {
                if (Checker.inRangeInclusive(method.getRange().get().begin.line, method.getRange().get().end.line, location)) {
                    methodName = method.getNameAsString();
                    break;
                }
            }
        }

        return methodName;
    }

    /**
     * Determine if the provided methodName has been declared in an interface or not
     * @param methodName Name of method to be resolved
     * @return true, method name has been declared initially in an interface definition
     */
    public boolean isMethodDefinedInInterface(String methodName) {

        boolean methodDeclaredInInterface = false;

        // When specific method is visible in class, figure out if it has been defined
        // in an interface or not
        if (hasMethodDefined(methodName))
        {
            // Get type declaration of given class, so we can resolve method declaration outside
            // the class definition
            ClassOrInterfaceDeclaration class4Analysis = Navigator.demandClass(_cu, _qname);
            ReferenceTypeDeclaration rtd = JavaParserFacade.get(_symbolSolver).getTypeDeclaration(class4Analysis);
            List<ReferenceType> rt = rtd.asClass().getAllInterfaces();

            // Determine for each ancestor if it is an interface declaration
            for( ReferenceType ancestor : rt)
            {
                ReferenceTypeDeclaration rtd_ancestor = ancestor.getTypeDeclaration();

                // When interface declaration and not one of the ignored packages
                // Check if provided methodName is present in the stream of declared methods
                // of this interface
                if (!isIgnoredPackage(rtd_ancestor) &&
                        rtd_ancestor.getDeclaredMethods().stream().anyMatch(method -> method.getName().equals(methodName)))
                {
                    methodDeclaredInInterface = true;
                    break;
                }
            }
        }

        return methodDeclaredInInterface;
    }

    /**
     * Is method defined in this specific class [Note: no check on return types and parameters yet in this case]
     * @param methodName Name of method to be looked up
     * @return true, method name exists in class definition
     */
    public boolean hasMethodDefined(String methodName) {
        boolean methodFound = false;

        for (MethodDeclaration methodDecl : getMethodDeclarations())
        {
            methodFound = methodDecl.getName().toString().equals(methodName) || methodFound;
        }

        return methodFound;
    }
}
