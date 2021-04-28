import sympy
import re
x=sympy.Symbol('x')
fo = open("input.txt","r")
str1 = fo.read()
foo = open ("outputcorrect.txt","w")
str1 = re.sub(r"\b0*([1-9][0-9]*|0)", r"\1", str1)
fx1 = sympy.sympify(str1)
y11 = sympy.diff(fx1,x)
for i in range(0,100):
    foo.write(str(y11.evalf(subs = {x:i})))
    foo.write("\n")
for i in range(-100,-1):
    foo.write(str(y11.evalf(subs = {x:i})))
    foo.write("\n")
fo2 = open("output.txt","r")
str1 = fo2.read()
foo2 = open ("outputunknown.txt","w")
str1 = re.sub(r"\b0*([1-9][0-9]*|0)", r"\1", str1)
y11 = sympy.sympify(str1)
for i in range(0,100):
    foo2.write(str(y11.evalf(subs = {x:i})))
    foo2.write("\n")
for i in range(-100,-1):
    foo2.write(str(y11.evalf(subs = {x:i})))
    foo2.write("\n")
fo.close()
foo.close()
fo2.close()
foo2.close()
