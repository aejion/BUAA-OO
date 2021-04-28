from xeger import Xeger
x = Xeger(limit = 60)
fo = open("input.txt","w")
for num in range(1,15):
    fo.write(x.xeger("[+-]{1,2}[\ \t]*([0-9]*[\ \t]*(\*)[\ \t]*)?x[\ \t]*((\*){2}[\ \t]*[+-]?[0-9]*)?"))
fo.close()
