clear
echo "Clean ..."
./clean.sh

echo "Build basic package"
mvn package

echo "Build all-include package"
mvn compile assembly:single
