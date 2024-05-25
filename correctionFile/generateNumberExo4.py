import random

def generate_random_numbers(seed, count):
    random.seed(seed)
    numbers = [random.randint(10, 10000) for _ in range(count)]
    return numbers

def main():
    seed = random.randint(10, 10000)
    count = 1
    numbers = generate_random_numbers(seed, count)
    print(" ".join(map(str, numbers)))

main()