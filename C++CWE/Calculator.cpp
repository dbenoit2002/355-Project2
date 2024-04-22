#include <iostream>
#include <limits>

using namespace std;

int main() {
    int userInput;
    float num1, num2;
    bool endFlag = false;

    while(!endFlag) {
        cout << "Select an option: " << endl;
        cout << "0: exit" << endl;
        cout << "1: addition" << endl;
        cout << "2: subtraction" << endl;
        cout << "3: multiplication" << endl;
        cout << "4: division" << endl;

        cin >> userInput;
        if(cin) {
            if(userInput == 0) {
                cout << "Goodbye" << endl;
                endFlag = true;
            }
            else if(userInput < 0 || userInput > 4){
                cout << "Please enter a valid option" << endl;
            }
            else {
                cout << "Enter two numbers: " << endl;
                cin >> num1 >> num2;
                if(cin) {
                    cout << "Result: ";
                    if(userInput == 1) {
                        cout << num1 + num2 << endl;
                    }
                    else if(userInput == 2) {
                        cout << num1 - num2 << endl;
                    }
                    else if(userInput == 3) {
                        cout << num1 * num2 << endl;
                    }
                    else if(userInput == 4) {
                        cout << num1 / num2 << endl;
                    }
                }
                else {
                    cin.clear();
                    cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
                    cout << "Please enter a number" << endl;
                }
            }
        }
        else {
            cin.clear();
            cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
            cout << "Please enter an integer" << endl;
        }
    }
}