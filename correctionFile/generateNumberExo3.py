import random

def generate_random_numbers(seed, count):
    random.seed(seed)
    numbers = [random.randint(1, 100) for _ in range(count)]
    return numbers

def main():
    seed = random.randint(0, 100)
    count = 10
    numbers = generate_random_numbers(seed, count)
    print(" ".join(map(str, numbers)))

main()