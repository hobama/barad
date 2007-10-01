package barad.instrument;

public enum Names {
	//======================
	//VARIABLES
	//======================
	//Integer
	IVAR("barad/symboliclibrary/integers/IVAR"),
	IVAR_SIGNATURE("()V"),
	ICONST("barad/symboliclibrary/integers/ICONST"),
	ICONST_SIGNATURE("(I)V"),
	INTEGER_INTERFACE("Lbarad/symboliclibrary/integers/IntegerInterface;"),
	//Float
	FVAR("barad/symboliclibrary/floats/FVAR"),
	FVAR_SIGNATURE("()V"),
	FCONST("barad/symboliclibrary/floats/FCONST"),
	FCONST_SIGNATURE("(F)V"),
	FLOAT_INTERFACE("Lbarad/symboliclibrary/floats/FloatInterface;"),
	//String
	SYMBOLICSTRING("barad/symboliclibrary/string/SymbolicString"),
	SYMBOLICSTRING_VAR_SIGNATURE("()V"),
	SYMBOLICSTRING_CONST_SIGNATURE("(Ljava/lang/String;)V"),
	SYMBOLICSTRING_INTRFACE("Lbarad/symboliclibrary/string/StringInterface;"),
	STRING("Ljava/lang/String;"),
	//======================
	//PATH
	//======================
	PATH("barad/symboliclibrary/path/Path"),
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
	//String
	EQUALS("barad/symboliclibrary/string/Equals"),
	STRING_PATH_CONSTRAINT_SIGNATURE("(Lbarad/symboliclibrary/string/StringInterface;Lbarad/symboliclibrary/string/StringInterface;)V"),
	//======================
	//ENTITIES
	//======================
	//Integer
	IADD("barad/symboliclibrary/integers/IADD"),
	IDIV("barad/symboliclibrary/integers/IDIV"),
	IMUL("barad/symboliclibrary/integers/IMUL"),
	ISUB("barad/symboliclibrary/integers/ISUB"),
	INTEGER_OPERATION_SIGNATURE("(Lbarad/symboliclibrary/integers/IntegerInterface;Lbarad/symboliclibrary/integers/IntegerInterface;)V"),
	//Floats
	//======================
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
