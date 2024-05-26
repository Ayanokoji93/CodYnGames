function main() {
    $input = readline();
    $number = intval($input);

    $result = countDigits($number);
    echo "Le nombre de chiffres de $number est $result\n";
}
main();

?>
