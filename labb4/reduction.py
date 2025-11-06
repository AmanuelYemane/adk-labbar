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

num_actors = m + 2

isolated_nodes = []
for node in range(v):
    if all(node+1 not in edge for edge in edges):
        isolated_nodes.append(node + 1)

num_roles = v + len(isolated_nodes) + 3
num_scenes = e + len(isolated_nodes) + 2

print(num_roles)
print(num_scenes)
print(num_actors)

## Villkor typ 1 ##
print("1 1") # p1
print("1 2") # p2

# garantera att vi får en roll som kan spela mot p1 och p2
roles = str(m) + " " + " ".join(str(actor + 3) for actor in range(m))
print(roles)

# skapa en roll för alla hörn med kant
for _ in range(v):
    print(roles)

# skapa jokerroller, dvs roller som kan spela mot varje isolerat hörn
for _ in isolated_nodes:
    print(str(m + 2) + " " + " ".join(str(actor + 1) for actor in range(m + 2)))

## Villkor typ 2 ##

# en scen för p1 och p2
print("2 1 3")
print("2 2 3")

# en scen för varje kant
for edge in edges:
    print("2 " + str(edge[0] + 3) + " " + str(edge[1] + 3))
        
# en scen för varje isolerat hörn
joker_start = 3 + v + 1
for index, node in enumerate(isolated_nodes):
    joker_id = joker_start + index
    print("2 " + str(node + 3) + " " + str(joker_id))