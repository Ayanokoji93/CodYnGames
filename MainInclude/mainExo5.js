function main() {
    const rl = readline.createInterface({
        input: process.stdin,
        output: process.stdout
    });

    rl.question("", (input) => {
        const number = parseInt(input, 10);
            const result = countDigits(number);
            console.log(`Le nombre de chiffres de ${number} est ${result}`);
        rl.close();
    });
}
main();
