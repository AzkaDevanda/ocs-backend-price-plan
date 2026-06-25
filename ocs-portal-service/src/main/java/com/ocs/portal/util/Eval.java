package com.ocs.portal.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.ocs.portal.dto.request.balanceAdjustment.Function;
import com.ocs.portal.dto.request.balanceAdjustment.Operator;
import com.ocs.portal.dto.request.balanceAdjustment.Span;
import com.ocs.portal.dto.request.balanceAdjustment.Token;
import com.ocs.portal.dto.request.balanceAdjustment.Variable;

public class Eval {
  protected static int drg = 82;
  
  private static List<Variable> variableList = new ArrayList<>();
  
  private static List<Operator> operatorList = new ArrayList<>();
  
  private static List<Function> functionList = new ArrayList<>();
  
  static {
    variableList.add(new Variable("TB", 1.099511627776E12D));
    variableList.add(new Variable("STEFAN_BOLTZMANN", 5.6704E-8D));
    variableList.add(new Variable("SPEED_OF_LIGHT", 2.99792458E8D));
    variableList.add(new Variable("RYDBERG", 1.0973731568549E7D));
    variableList.add(new Variable("PYTHAGORAS", Math.sqrt(2.0D)));
    variableList.add(new Variable("PROTON_MASS", 1.67262158E-27D));
    variableList.add(new Variable("PROTON_ELECTRON_MASS_RATIO", 1836.1526675D));
    variableList.add(new Variable("PLANCK", 6.62606876E-34D));
    variableList.add(new Variable("PI", Math.PI));
    variableList.add(new Variable("NEWTON", 6.673E-11D));
    variableList.add(new Variable("MOLAR_GAS", 8.314472D));
    variableList.add(new Variable("MIN_INT", -2.147483648E9D));
    variableList.add(new Variable("MIN_DEC", -1.7976931348623157E308D));
    variableList.add(new Variable("MB", 1048576.0D));
    variableList.add(new Variable("MAX_INT", 2.147483647E9D));
    variableList.add(new Variable("MAX_DEC", Double.MAX_VALUE));
    variableList.add(new Variable("MAGNETIC_FLUX", 2.067833636E-15D));
    variableList.add(new Variable("MAGNETIC", 1.2566370614359173E-6D));
    variableList.add(new Variable("MADELUNG", 2.0531987328D));
    variableList.add(new Variable("KHINTCHINE", 2.685452001D));
    variableList.add(new Variable("KB", 1024.0D));
    variableList.add(new Variable("INVERSE_FINE_STRUCTURE", 137.03599976D));
    variableList.add(new Variable("IMAGINARY_UNIT", Math.sqrt(-1.0D)));
    variableList.add(new Variable("GRAVITY", 9.80665D));
    variableList.add(new Variable("GB", 1.073741824E9D));
    variableList.add(new Variable("FINE_STRUCTURE", 0.007297352533D));
    variableList.add(new Variable("FEIGENBAUM", 2.5029078750958926D));
    variableList.add(new Variable("FARADAY", 96485.3415D));
    variableList.add(new Variable("EULER", 0.5772156649015329D));
    variableList.add(new Variable("ELEMENTARY_CHARGE", 1.602176462E-19D));
    variableList.add(new Variable("ELECTRON_VOLT", 1.602176462E-19D));
    variableList.add(new Variable("ELECTRON_MASS", 9.10938188E-31D));
    variableList.add(new Variable("ELECTRIC_CONSTANT", 8.854187817E-12D));
    variableList.add(new Variable("E", Math.E));
    variableList.add(new Variable("CONDUCTANCE_QUANTUM", 7.748091696E-5D));
    variableList.add(new Variable("CATALAN", 0.915965594D));
    variableList.add(new Variable("BOLTZMANN", 1.3806503E-23D));
    variableList.add(new Variable("AVOGADRO", 6.02214199E23D));
    variableList.add(new Variable("ATOMIC_MASS", 1.66053873E-27D));
    variableList.add(new Variable("APERY", 1.2020569031595942D));
    operatorList.add(new Operator("||", (byte)11));
    operatorList.add(new Operator("xor", (byte)8));
    operatorList.add(new Operator("or", (byte)9));
    operatorList.add(new Operator("mod", (byte)2));
    operatorList.add(new Operator("posmod", (byte)2));
    operatorList.add(new Operator("and", (byte)7));
    operatorList.add(new Operator("^", (byte)1));
    operatorList.add(new Operator(">>>", (byte)4));
    operatorList.add(new Operator(">>", (byte)4));
    operatorList.add(new Operator(">=", (byte)5));
    operatorList.add(new Operator(">", (byte)5));
    operatorList.add(new Operator("==", (byte)6));
    operatorList.add(new Operator("<>", (byte)6));
    operatorList.add(new Operator("<=", (byte)5));
    operatorList.add(new Operator("<<", (byte)4));
    operatorList.add(new Operator("<", (byte)5));
    operatorList.add(new Operator("/", (byte)2));
    operatorList.add(new Operator("-", (byte)3));
    operatorList.add(new Operator("+", (byte)3));
    operatorList.add(new Operator("**", (byte)1));
    operatorList.add(new Operator("*", (byte)2));
    operatorList.add(new Operator("&&", (byte)10));
    operatorList.add(new Operator("%", (byte)2));
    operatorList.add(new Operator("!=", (byte)6));
    functionList.add(new Function("varr", 2));
    functionList.add(new Function("var", 2));
    functionList.add(new Function("trunc", 2));
    functionList.add(new Function("tanh", 1));
    functionList.add(new Function("tan", 1));
    functionList.add(new Function("sum", 0));
    functionList.add(new Function("stddev", 0));
    functionList.add(new Function("sqrt", 1));
    functionList.add(new Function("sinh", 1));
    functionList.add(new Function("sin", 1));
    functionList.add(new Function("sign", 1));
    functionList.add(new Function("sech", 1));
    functionList.add(new Function("sec", 1));
    functionList.add(new Function("round", 2));
    functionList.add(new Function("random", 1));
    functionList.add(new Function("rad", 1));
    functionList.add(new Function("pow", 2));
    functionList.add(new Function("permr", 2));
    functionList.add(new Function("perm", 1));
    functionList.add(new Function("min", 0));
    functionList.add(new Function("max", 0));
    functionList.add(new Function("log2", 1));
    functionList.add(new Function("log10", 1));
    functionList.add(new Function("log", 1));
    functionList.add(new Function("hypot", 2));
    functionList.add(new Function("frac", 1));
    functionList.add(new Function("floor", 1));
    functionList.add(new Function("fact", 1));
    functionList.add(new Function("exp2", 1));
    functionList.add(new Function("exp10", 1));
    functionList.add(new Function("exp", 1));
    functionList.add(new Function("deg", 1));
    functionList.add(new Function("cur", 1));
    functionList.add(new Function("csch", 1));
    functionList.add(new Function("csc", 1));
    functionList.add(new Function("count", 0));
    functionList.add(new Function("coth", 1));
    functionList.add(new Function("cot", 1));
    functionList.add(new Function("cosh", 1));
    functionList.add(new Function("cos", 1));
    functionList.add(new Function("combr", 2));
    functionList.add(new Function("comb", 2));
    functionList.add(new Function("ceil", 1));
    functionList.add(new Function("avg", 0));
    functionList.add(new Function("atanh", 1));
    functionList.add(new Function("atan2", 2));
    functionList.add(new Function("atan", 1));
    functionList.add(new Function("asinh", 1));
    functionList.add(new Function("asin", 1));
    functionList.add(new Function("asech", 1));
    functionList.add(new Function("asec", 1));
    functionList.add(new Function("acsch", 1));
    functionList.add(new Function("acsc", 1));
    functionList.add(new Function("acoth", 1));
    functionList.add(new Function("acot", 1));
    functionList.add(new Function("acosh", 1));
    functionList.add(new Function("acos", 1));
    functionList.add(new Function("abs", 1));
  }
  
  public static String calculate(String expression) {
    if (!ParenthesesCheck.parenthesesValid(expression))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mismatched parentheses in expression.");
    List<Token> tokens = Tokenzation.rastavljanjeIzraza(expression, variableList, operatorList, functionList);
    StringBuffer sbInfo = new StringBuffer();
    if (!ExpressionCheck.starting(tokens, new Span(), sbInfo))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, sbInfo.toString());
    List<String> rez = Calculation.result(tokens);
    return ((String)rez.get(rez.size() - 1)).trim();
  }

}
