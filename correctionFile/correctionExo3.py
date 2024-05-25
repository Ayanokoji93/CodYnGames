def main():
    notes = []
    Som = 0.0

    for i in range(10):
        note = float(input())
        notes.append(note)
        Som += note

    print(Som / 10)

main()
