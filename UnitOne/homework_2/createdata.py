from xeger import Xeger
x = Xeger(limit = 10)
fo = open("input.txt","w")
allint = "[+-]?[0-9]+";
index = "(\\*{2}" + allint + ")";
trig = "(" + "(sin|cos)\\(x\\)" + index + "?" + ")";
pow1 = "(" + "x" + "(\\*{2}" + "[0-9]{1,4})" + "?" + ")";
finalfac = "(" + pow1 + "|" + trig + "|" + allint + ")";
term = "(" + "[+-]?" + finalfac + "(\\*" + finalfac + ")*" + ")";
finalstr = "[+-]?" + term + "([+-]" + term + ")*";
for num in range(1,2):
    fo.write(x.xeger(finalstr))
fo.close()
