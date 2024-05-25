import random

def generate_random_numbers(seed, count):
    random.seed(seed)
    numbers = [random.randint(1, 100) for _ in range(count)]
    return numbers

def main():
    seed = random.randint(0, 100)  # Vous pouvez le rendre dynamique
    count = 2  # Par exemple, générer 2 nombres aléatoires
    numbers = generate_random_numbers(seed, count)
    print(" ".join(map(str, numbers)))

main()