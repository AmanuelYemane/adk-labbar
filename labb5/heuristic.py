import sys

def read_int():
    return int(sys.stdin.readline().strip())

def read_line():
    line = list(map(int, sys.stdin.readline().split()))
    return line[1:]

def assign_super_actor():
    for i in range(1, n):
        if not super_actor_is_used[i]:
            super_actor_is_used[i] = True
            return k + i
    return None

def can_use_actor(actor, role):
    # Kolla alla grannar (roller som är i samma scener som denna roll)
    for neighbor in neighbors[role]:
        # Om en granne redan har denna skådespelare har vi en konflikt
        if assigned_roles[neighbor] == actor:
            return False
    return True

def assign_actor_to_role(role):
    # Om rollen redan är tilldelad, gör inget
    if assigned_roles[role] != 0:
        return

    actor = 0
    
    # Försök hitta en vanlig skådis som är tillåten för rollen och som inte krockar med grannarna
    for cand in actors_in_roles[role]:
        if cand == 1 or cand == 2:
            continue # Hoppa över divorna
        
        # Kolla om det finns någon konflikt
        if can_use_actor(cand, role):
            actor = cand
            break

    # Om ingen vanlig skådis funkar, ta en superskådis
    if actor == 0:
        actor = assign_super_actor()

    # Uppdatera datastrukturerna
    assigned_roles[role] = actor
    used_actors_and_their_roles[actor].append(role)


# Huvudprogram

n = read_int() # roller
s = read_int() # scener
k = read_int() # vanliga skådisar

actors_in_roles = [[] for _ in range(n + 1)]
roles_in_scenes = [[] for _ in range(s + 1)]

used_actors_and_their_roles = [[] for _ in range(k + n)]
assigned_roles = [0 for _ in range(n + 1)]

neighbors = [[] for _ in range(n + 1)]
super_actor_is_used = [False] * (n + 1)

for i in range(1, n + 1):
    actors_in_roles[i] = read_line()

for i in range(1, s + 1):
    roles_in_scenes[i] = read_line()

# Gå igenom och spara alla roller som möts
for i in range(1, s + 1):
    scene_roles = roles_in_scenes[i]
    for r1 in scene_roles:
        for r2 in scene_roles:
            if r1 != r2:
                neighbors[r1].append(r2)

diva1_role = 0
diva2_role = 0
found_pair = False

# Gå igenom alla roller diva 1 kan ha
for r1 in range(1, n + 1):
    if 1 in actors_in_roles[r1]:
        
        # För varje r1, testa om det finns en r2 som funkar
        for r2 in range(1, n + 1):
            if r1 == r2: continue # Kan inte ha samma roll
            
            if 2 in actors_in_roles[r2] and r2 not in neighbors[r1]:
                diva1_role = r1
                diva2_role = r2
                found_pair = True
                break
    
    if found_pair:
        break

# Tilldela rollerna om vi hittade dem
if found_pair:
    # Boka diva 1
    assigned_roles[diva1_role] = 1
    used_actors_and_their_roles[1].append(diva1_role)
    
    # Boka diva 2
    assigned_roles[diva2_role] = 2
    used_actors_and_their_roles[2].append(diva2_role)

# Tilldela resterande roller
for role in range(1, n + 1):
    assign_actor_to_role(role)

# Räkna ihop antalet använda skådisar
num_of_actors_used = 0
for roles in used_actors_and_their_roles:
    if len(roles) > 0:
        num_of_actors_used += 1

print(num_of_actors_used)

# Skriv ut de använda skådisarna och deras roller
for i in range(1, len(used_actors_and_their_roles)):
    if len(used_actors_and_their_roles[i]) > 0:
        print(
            i,
            len(used_actors_and_their_roles[i]),
            " ".join(str(role) for role in used_actors_and_their_roles[i])
        )