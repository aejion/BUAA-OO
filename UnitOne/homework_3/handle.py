import sympy
import re
x=sympy.Symbol('x')
str1 = "cos(x)*-9-+cos(cos(x**2))**8+-x**-1*-7++sin(x**9)**-4*x-x**2"
str1 = re.sub(r"\b0*([1-9][0-9]*|0)", r"\1", str1)
fx1 = sympy.sympify(str1)
y11 = sympy.diff(fx1,x)
print(y11)
print(str(y11.evalf(subs = {x:3})))
str1 = "(-sin(x))*-9+8*cos(cos(x**2))**7*sin(cos(x**2))*(-sin(x**2)*(2*x))-(-1)*-7+(-4*sin(x**9)**-5*cos(x**9)*(9*x**8))*x+sin(x**9)**-4-2*x"
fx1 = sympy.sympify(str1)
print(str(fx1.evalf(subs = {x:3})))
