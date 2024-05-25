def factorial(n):
    result = 1
    for i in range(1, n + 1):
        result *= i
    return result
def main():
    number = int(input())
    result = factorial(number)
    print(result)
main()
