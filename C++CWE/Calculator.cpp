#include <iostream>
#include <limits>
#include <thread>
#include <mutex>
#include <vector>

using namespace std;

float conMultiplication(int num1, int num2);
void multHelper(float *result, mutex *mutex, int num);

int main() {
    int userInput = -1; //CWE-457
    float num1, num2 = -1;
    bool endFlag = false;
    int loopCount = 1;

    while(!endFlag) {
        cout << "Select an option: " << endl;
        cout << "0: exit" << endl;
        cout << "1: addition" << endl;
        cout << "2: subtraction" << endl;
        cout << "3: multiplication" << endl;
        cout << "4: multithreaded multiplication" << endl;
        cout << "5: division" << endl;

        cin >> userInput;
        if(cin) {
            if(userInput == 0) { //CWE-480
                cout << "Program ran " << loopCount << " times!" << endl;
                cout << "Goodbye" << endl;
                endFlag = true;
            }
            else if(userInput > 5){ //CWE-839
                cout << "Please enter a valid option" << endl;
            }
            else if(userInput < 0){
                cout << "Please enter a valid option" << endl;
            }
            else {
                cout << "Enter two numbers: " << endl;
                cin >> num1 >> num2;
                if(cin) {
                    cout << "Result: ";
                    if(userInput == 1) { //CWE-482
                        cout << num1 + num2 << endl;
                    }
                    else if(userInput == 2) {
                        cout << num1 - num2 << endl;
                    }
                    else if(userInput == 3) {
                        cout << num1 * num2 << endl;
                    }
                    else if(userInput == 4) {
                        cout << conMultiplication(num1, num2) << endl;
                    }
                    else if(userInput == 5) {
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
        if(loopCount != INT_MAX) { //CWE-128
            loopCount++;
        }
    }
}

float conMultiplication(int num1, int num2) {
    mutex mutex;
    float result = 0;
    vector<thread> threads;
    bool negFlag = false; 
    if(num2 < 0) { //covers when num2 is negative
        negFlag = true;
        num2 *= -1;
    }

    for(int i = 0; i < num2; i++) {
        thread newThread(multHelper, &result, &mutex, num1);
        threads.push_back(move(newThread));
    }
    vector<thread>::iterator curThread = threads.begin();
    while(curThread != threads.end()) {
        curThread->join();
        curThread++;
    }
    if(negFlag) {
        result *= -1;
    }
    return result;
}

void multHelper(float *result, mutex *mutex, int num) {
    (*mutex).lock(); //CWE-366
    *result += num;
    (*mutex).unlock();
}