import sys

def read_int():
    return int(sys.stdin.readline().strip())

def read_edge():
    a, b = map(int, sys.stdin.readline().split())
    return a, b

v = read_int()
e = read_int()
m = read_int()

# ignorera onödigt stora m-värden
if (m > v):
    m = v

edges = []
for _ in range(e):
    edges.append(read_edge())

# O(VE)
isolated_nodes = []
for node in range(v):
    if all(node+1 not in edge for edge in edges):
        isolated_nodes.append(node + 1)

num_roles = v + len(isolated_nodes) + 3
num_scenes = e + len(isolated_nodes) + 2
num_actors = m + 2

print(num_roles)
print(num_scenes)
print(num_actors)

## Villkor typ 1 ##
print("1 1") # roll för p1
print("1 2") # roll för p2

# garantera att vi får en roll som kan spela mot p1 och p2
actors = str(m) + " " + " ".join(str(actor + 3) for actor in range(m))
print(actors)

# skapa en roll för alla hörn med kant
for _ in range(v):
    print(actors)

# skapa jokerroller, dvs roller som kan spela mot varje isolerat hörn, tillåt skådis p1 och p2 för fallet då m=1
for _ in isolated_nodes:
    print(str(m + 2) + " " + " ".join(str(actor + 1) for actor in range(m + 2)))

## Villkor typ 2 ##

# en scen för p1 och p2
print("2 1 3")
print("2 2 3")

# en scen för varje kant, dubbelkanter blir dubbla scener vilket inte påverkar korrektheten
for edge in edges:
    print("2 " + str(edge[0] + 3) + " " + str(edge[1] + 3))
        
# en scen för varje isolerat hörn
joker_start = 3 + v + 1
for index, node in enumerate(isolated_nodes):
    joker_id = joker_start + index
    print("2 " + str(node + 3) + " " + str(joker_id))

# Reduktionen är korrekt eftersom instansen till rollbesättningsproblemet kommer vara korrekt i de fall då instansen till graffärgningen är m-färgningsbar.
# Varje nod i grafen motsvaras av en roll i rollbesättningsproblemet. Vi skapar också en skådis för varje färg m som användnings till nodfärgningen, och lägger
# till 2 skådisar som motsvarar p1 och p2 etersom vi har speciella krav på dessa skådisar. Vi får alltså m st "färgskådisar" och 2 extra skådisar.
# 
# Om grafen är m-nodfärgningsbar vet vi att varje par av noder som har en eller flera kanter
# mellan sig kommer vara färgade i olika färger, det vill säga vi vet att de kommer vara olika roller i rollbesättningsproblemet. Därmed förljer att de kan medverka i samma scen,
# så vi skapar en scen för varje kant i grafen. En scen innebär att de två rollerna inte får spelas av samma skådespelare, vilket motsvarar att två grannar i grafen inte får samma färg.
# Men vi har också kravet att varje roll ska förekomma i minst en scen, det vill säga att vi måste hantera de isolerade noderna som inte har någon kant i grafen. För varje sådan roll 
# lägger vi då till en jokerroll som kan spela i en scen med den isolerade noden, och vi tillåter jokerrollen att spelas av alla skådisar (inklusive p1 och p2). 
# 
# För att vi även ska uppfylla kraven om att p1 och p2 ska ha minst en
# roll och ingå i minst en scen, men att de inte ska spela mot varandra, skapar vi en roll för de båda och en scen för båda dessa roller där de spelar mot en tredje roll som väljs från
# någon av våra m st färgskådisar. Då garanterar vi att vi alltid uppfyller kraven för p1 och p2 även om grafen exempelvis bara består av en nod.

# Reduktionen ger oss att rollbesättningsproblemet är NP-svårt, men vi kan också veta att det ligger i NP och därmed är NP-fullständigt. Det vet vi eftersom
# vi kan göra reduktionen åt andra hållet där vi från en instans av rollbesättningsproblemet skapar en intsans av graffärgning som blir korrekt bara om instansen till rollbesättningen är korrekt.
# Denna reduktion kan göras på liknande premisser, och utgår från att vi vet att en scen mellan olika roller innebär att rollerna spelas av olika skådisar, alltså att de ska ha en kant mellan sig i grafen 
# och att de därmed måste ha olika färger. Om vi har m+2 skådisar kommer detta leda till att grafen kommer kunna färgas med m färger.