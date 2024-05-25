import random

def generate_random_numbers(seed, count):
    random.seed(seed)
    numbers = [random.randint(1, 10) for _ in range(count)]
    return numbers

def main():
    seed = random.randint(1, 10)
    count = 1
    numbers = generate_random_numbers(seed, count)
    print(" ".join(map(str, numbers)))

main()