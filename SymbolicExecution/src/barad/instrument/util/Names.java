package barad.instrument.util;

public enum Names {
	
	//======================
	//VARIABLES
	//======================
	
	//Integer
	INTEGER("java/lang/Integer"),
	
	//Float
	FLOAT("java/lang/Float"),
	
	//Symbolic Integer
	IVAR("barad/symboliclibrary/integers/IVAR"),
	IVAR_SIGNATURE("()V"),
	ICONST("barad/symboliclibrary/integers/ICONST"),
	ICONST_SIGNATURE("(I)V"),
	INTEGER_INTERFACE("barad/symboliclibrary/integers/IntegerInterface"),
	SYMBOLIC_INTEGER_ENTITY("barad/symboliclibrary/integers/SymbolicIntegerEntity"),
	
	//Symbolic Float
	FVAR("barad/symboliclibrary/floats/FVAR"),
	FVAR_SIGNATURE("()V"),
	FCONST("barad/symboliclibrary/floats/FCONST"),
	FCONST_SIGNATURE("(F)V"),
	FLOAT_INTERFACE("barad/symboliclibrary/floats/FloatInterface"),
	SYMBOLIC_FLOAT_ENTITY("barad/symboliclibrary/floats/SymbolicFloatEntity"),
	
	//String
	STRING("java/lang/String"),
	STRING_BUILDER("java/lang/StringBuilder"),
	TO_STRING("toString"),
	APPEND("append"),
	APPEND_DESCRIPTOR1("(Ljava/lang/String;)Ljava/lang/StringBuilder;"),
	APPEND_DESCRIPTOR2("(F)Ljava/lang/StringBuilder;"),
	STRING_VALUE_OF("valueOf"),
	STRING_VALUE_OF_DESCRIPTOR1("(Ljava/lang/Object;)Ljava/lang/String;"),
	STRING_VALUE_OF_DESCRIPTOR2("(I)Ljava/lang/String;"),
	STRING_VALUE_OF_DESCRIPTOR3("(D)Ljava/lang/String;"),
	STRING_VALUE_OF_DESCRIPTOR4("(F)Ljava/lang/String;"),
	STRING_VALUE_OF_DESCRIPTOR5("(J)Ljava/lang/String;"),
	SUBSTRING("substring"),
	SUBSTRING_SIGNATURE1("(I)Ljava/lang/String;"),
	SUBSTRING_SIGNATURE2("(II)Ljava/lang/String"),
	TRIM("trim"),
	TRIM_SIGNATURE("()Ljava/lang/String;"),
	
	//Symbolic String
	SYMBOLIC_STRING("barad/symboliclibrary/strings/SymbolicString"),
	SYMBOLIC_STRING_BUILDER("barad/symboliclibrary/strings/SymbolicStringBuilder"),
	SYMBOLIC_STRING_VALUE_OF_DESCRIPTOR1("(Ljava/lang/Object;)Lbarad/symboliclibrary/strings/StringInterface;"),
	SYMBOLIC_STRING_VALUE_OF_DESCRIPTOR2("(Lbarad/symboliclibrary/integers/IntegerInterface;)Lbarad/symboliclibrary/strings/StringInterface;"),
	SYMBOLIC_STRING_VALUE_OF_DESCRIPTOR3("(Lbarad/symboliclibrary/floats/FloatInterface;)Lbarad/symboliclibrary/strings/StringInterface;"),
	TO_SYMBOLIC_STRING("toSymbolicString"),
	TO_SYMBOLIC_STRING_DESCRIPTOR("()Lbarad/symboliclibrary/strings/StringInterface;"),
	SYMBOLIC_APPEND_DESCRIPTOR1("(Lbarad/symboliclibrary/strings/StringInterface;)Lbarad/symboliclibrary/strings/SymbolicStringBuilder;"),
	SYMBOLIC_APPEND_DESCRIPTOR2("(Lbarad/symboliclibrary/floats/FloatInterface;)Lbarad/symboliclibrary/strings/SymbolicStringBuilder;"),
	SYMBOLIC_STRING_VOID_SIGNATURE("()V"),
	SYMBOLIC_STRING_VAR_SIGNATURE("(I)V"),
	SYMBOLIC_STRING_CONST_SIGNATURE("(Ljava/lang/String;)V"),
	SYMBOLIC_STRING_INTRFACE("barad/symboliclibrary/strings/StringInterface"),
	SYMBOLIC_SUBSTRING_SIGNATURE1("(Lbarad/symboliclibrary/integers/ICONST;)Lbarad/symboliclibrary/strings/StringInterface;"),
	SYMBOLIC_SUBSTRING_SIGNATURE2("(Lbarad/symboliclibrary/integers/ICONST;Lbarad/symboliclibrary/integers/ICONST;)Lbarad/symboliclibrary/strings/StringInterface;"),
	SYMBOLIC_TRIM_DESCRIPTOR("()Lbarad/symboliclibrary/strings/StringInterface;"),
	
	//Symbolic Array
	SYMBOLIC_ARRAY("barad/symboliclibrary/arrays/SymbolicArray"),
	SYMBOLIC_ARRAY_SIGNATURE("(Lbarad/symboliclibrary/integers/ICONST;)V"),
	SYMBOLIC_ARRAY_ADD_ELEMENT_SIGNATURE("(Lbarad/symboliclibrary/integers/ICONST;Ljava/lang/Object;)V"),
	
	//SWT Widgets
	TEXT("org/eclipse/swt/widgets/Text"),
	LABEL("org/eclipse/swt/widgets/Label"),
	BUTTON("org/eclipse/swt/widgets/Button"),
	STYLED_TEXT("org/eclipse/swt/custom/StyledText"),
	COMBO("org/eclipse/swt/widgets/Combo"),
	COMPOSITE("org/eclipse/swt/widgets/Composite"),
	WIDGET("org/eclipse/swt/widgets/Widget"),
	
	//SWT layout
	LAYOUT("org/eclipse/swt/widgets/Layout"),
	FORM_LAYOUT("org/eclipse/swt/layout/FormLayout"),
	FORM_DATA("org/eclipse/swt/layout/FormData"),
	FORM_ATTACHMENT("org/eclipse/swt/layout/FormAttachment"),
	
	//SWT Event Listenters
	SELECTION_LISTENER("org/eclipse/swt/events/SelectionListener"),
	SELECTION_ADAPTER("org/eclipse/swt/events/SelectionAdapter"),
	
	//Symbolic Widgets
	SYMBOLIC_TEXT("barad/symboliclibrary/ui/widgets/SymbolicText"),
	SYMBOLIC_LABEL("barad/symboliclibrary/ui/widgets/SymbolicLabel"),
	SYMBOLIC_BUTTON("barad/symboliclibrary/ui/widgets/SymbolicButton"),
	SYMBOLIC_COMBO("barad/symboliclibrary/ui/widgets/SymbolicCombo"),
	SYMBOLIC_COMPOSITE("barad/symboliclibrary/ui/widgets/SymbolicComposite"),
	SYMBOLIC_WIDGET("barad/symboliclibrary/ui/widgets/SymbolicWidget"),
	
	//Symbolic layout
	SYMBOLIC_LAYOUT("barad/symboliclibrary/ui/layout/SymbolicLayout"),
	SYMBOLIC_FORM_LAYOUT("barad/symboliclibrary/ui/layout/SymbolicFormLayout"),
	SYMBOLIC_FORM_DATA("barad/symboliclibrary/ui/layout/SymbolicFormData"),
	SYMBOLIC_FORM_ATTACHMENT("barad/symboliclibrary/ui/layout/SymbolicFormAttachment"),
	
	//Symbolic Event Listenters
	SYMBOLIC_SELECTION_LISTENER("barad/symboliclibrary/ui/events/listeners/SymbolicSelectionListener"),
	SYMBOLIC_SELECTION_ADAPTER("barad/symboliclibrary/ui/events/listeners/SymbolicSelectionAdapter"),
	
	//SWT Events
	EVENT("org/eclipse/swt/events/TypedEvent"),
	SELECTION_EVENT("org/eclipse/swt/events/SelectionEvent"),
	
	//Symbolic Events
	SYMBOLIC_EVENT("barad/symboliclibrary/ui/events/SymbolicEvent"),
	SYMBOLIC_SELECTION_EVENT("barad/symboliclibrary/ui/events/SymbolicSelectionEvent"),
	
	//Other
	MATH("java/lang/Math"),
	SYSTEM("java/lang/System"),
	SYMBOLIC_SYSTEM("barad/symboliclibrary/auxiliary/SymbolicSystem"),
	SYMBOLIC_MATH("barad/symboliclibrary/auxiliary/SymbolicMath"),
	SYMBOLIC_ROUND_DESCRIPTOR("(Lbarad/symboliclibrary/floats/FloatInterface;)Lbarad/symboliclibrary/integers/IntegerInterface;"),
	ROUND("round"),
	EXIT("exit"),
	SYMBOLIC_EXIT_DESCRIPTOR("(Lbarad/symboliclibrary/integers/IntegerInterface;)V"),
	
	//======================
	//AUXILIARY
	//======================
	
	//SWT Composite
	COMPOSITE_CONSTRUCTOR("(Lorg/eclipse/swt/widgets/Composite;I)V"),
	
	//Symbolic Composite
	SYMBOLIC_COMPOSITE_CONSTRUCTOR("(Lbarad/symboliclibrary/ui/widgets/SymbolicComposite;Lbarad/symboliclibrary/integers/IntegerInterface;)V"),
	
	//Signatures
	VOID_SIGNATURE("()V"),
	CONSTRUCTOR("<init>"),
	MAIN_METHOD_SIGNATURE("([Ljava/lang/String;)V"),
	EQUALS_SIGNATURE("(Ljava/lang/Object;)Z"),
	
	//Other
	THIS("this"),
	MAIN_METHOD("main"),
	EQUALS("equals"),
	
	//======================
	//PATH
	//======================
	
	PATH("barad/symboliclibrary/path/Path"),
	PATH_ADD_INPUT_VARIABLE_SIGNATURE("(Ljava/lang/Object;)Ljava/lang/Object;"),
	PATH_ADD_LOCAL_FIELD_SIGNATURE("(Ljava/lang/Object;Ljava/lang/String;)V"),
	PATH_ADD_LOCAL_VARIABLE_SIGNATURE("(Ljava/lang/Object;I)Ljava/lang/Object;"),
	PATH_ADD_GET_VARIABLE_VALUE_SIGNATURE("(I)Ljava/lang/Object;"),
	PATH_ADD_BRANCH_CONSTRAINT("(Ljava/lang/Object;)Ljava/lang/Object;"),
	PATH_REMOVE_LAST_STATE_SIGNATURE("()V"),
	PATH_GENERATE_INPUTS_SIGNATURE("()V"),
	REVERSE_BRANCH_CONSTRAINTS_SIGNATURE("()V"),

	//======================
	//CONSTRAINTS
	//======================
	
	//Integer
	IFEQ("barad/symboliclibrary/path/integers/IFEQ"),
	IFNE("barad/symboliclibrary/path/integers/IFNE"),
	IFLT("barad/symboliclibrary/path/integers/IFLT"),
	IFLE("barad/symboliclibrary/path/integers/IFLE"),
	IFGT("barad/symboliclibrary/path/integers/IFGT"),
	IFGE("barad/symboliclibrary/path/integers/IFGE"),
	IF_ICMPEQ("barad/symboliclibrary/path/integers/IF_ICMPEQ"),
	IF_ICMPGE("barad/symboliclibrary/path/integers/IF_ICMPGE"),
	IF_ICMPGT("barad/symboliclibrary/path/integers/IF_ICMPGT"),
	IF_ICMPLE("barad/symboliclibrary/path/integers/IF_ICMPLE"),
	IF_ICMPLT("barad/symboliclibrary/path/integers/IF_ICMPLT"),
	IF_ICMPNE("barad/symboliclibrary/path/integers/IF_ICMPNE"),
	BINARY_INTEGER_PATH_CONSTRAINT_SIGNATURE("(Lbarad/symboliclibrary/integers/IntegerInterface;Lbarad/symboliclibrary/integers/IntegerInterface;)V"),
	UNARY_INTEGER_PATH_CONSTRAINT_SIGNATURE("(Lbarad/symboliclibrary/integers/IntegerInterface;)V"),
	
	//Float
	IF_FCMPEQ("barad/symboliclibrary/path/floats/IF_FCMPEQ"),
	IF_FCMPGE("barad/symboliclibrary/path/floats/IF_FCMPGE"),
	IF_FCMPGT("barad/symboliclibrary/path/floats/IF_FCMPGT"),
	IF_FCMPLE("barad/symboliclibrary/path/floats/IF_FCMPLE"),
	IF_FCMPLT("barad/symboliclibrary/path/floats/IF_FCMPLT"),
	IF_FCMPNE("barad/symboliclibrary/path/floats/IF_FCMPNE"),
	BINARY_FLOAT_PATH_CONSTRAINT_SIGNATURE("(Lbarad/symboliclibrary/floats/FloatInterface;Lbarad/symboliclibrary/floats/FloatInterface;)V"),
	
	//Symbolic String
	STRING_EQUALS("barad/symboliclibrary/path/strings/Equals"),
	STRING_NOT_EQUALS("barad/symboliclibrary/path/strings/NotEquals"),
	STRING_PATH_CONSTRAINT_SIGNATURE("(Lbarad/symboliclibrary/strings/StringInterface;Lbarad/symboliclibrary/strings/StringInterface;)V"),
	
	//======================
	//ENTITIES
	//======================
	
	//Integer
	IADD("barad/symboliclibrary/integers/IADD"),
	IDIV("barad/symboliclibrary/integers/IDIV"),
	IMUL("barad/symboliclibrary/integers/IMUL"),
	ISUB("barad/symboliclibrary/integers/ISUB"),
	INTEGER_BINARY_OPERATION_SIGNATURE("(Lbarad/symboliclibrary/integers/IntegerInterface;Lbarad/symboliclibrary/integers/IntegerInterface;)V"),
	
	//Floats
	FADD("barad/symboliclibrary/floats/FADD"),
	FDIV("barad/symboliclibrary/floats/FDIV"),
	FMUL("barad/symboliclibrary/floats/FMUL"),
	FSUB("barad/symboliclibrary/floats/FSUB"),
	FLOAT_OPERATION_SIGNATURE("(Lbarad/symboliclibrary/floats/FloatInterface;Lbarad/symboliclibrary/floats/FloatInterface;)V");
	
    private final String value;

    private Names(String name) {
        this.value = name;
    }
    public String getValue() {
    	return value;
    }
}
