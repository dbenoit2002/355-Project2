#include <iostream>
#include <limits>
#include <thread>
#include <mutex>
#include <vector>

using namespace std;

float conMultiplication(int num1, int num2);
void multHelper(float *result, mutex *mutex, int num);

//CWE-134 All our print functions are in cout form and we have avoided using printf to prevent format strings
int main() {
    int userInput = -1; //CWE-457
    float num1, num2 = -1;
    bool endFlag = false;
    int arrSizeInput = 0;
    int* arrSizePtr;
    int* arr = NULL;
    int addTotal = 0;
    string yesOrNo = "T";
    int numChange;
    int replaceNum;
    short replaceShort;
    int multiTotal = 1;
    int loopCount = 1;

    while(!endFlag) {
        cout << "Select an option: " << endl;
        cout << "0: exit" << endl;
        cout << "1: addition" << endl;
        cout << "2: subtraction" << endl;
        cout << "3: multiplication" << endl;
        cout << "4: multithreaded multiplication" << endl;
        cout << "5: division" << endl;
        cout << "6: vector addition" << endl;
        cout << "7: memory allocated multiplication" << endl;

        cin >> userInput;
        if(cin) {
            if(userInput == 0) { //CWE-480
                cout << "Program ran " << loopCount << " times!" << endl;
                cout << "Goodbye" << endl;
                endFlag = true;
            }
            else if(userInput > 7) { //CWE-839
                cout << "Please enter a valid option" << endl;
            }
            else if(userInput < 0) {
                cout << "Please enter a valid option" << endl;
            }
            else {
                if(userInput <= 5)
                {
                    cout << "Enter two numbers: " << endl;
                    cin >> num1 >> num2;
                }
                if(cin) {
                    if(userInput <= 5)
                    {
                        cout << "Result: ";
                    }
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
                    else if(userInput == 6) {
                        cout << "How many numbers would you like to add? (Must be at least 2)" << endl;
                        cin >> arrSizeInput;
                        if(arrSizeInput >= 2) //CWE-192
                        {
                            // Using std::vector to manage the collection of integers for addition.
                            
                            // The decision to use std::vector addresses multiple CWEs as follows:
                            // CWE-119: Protect against operations outside the bounds of memory buffers.
                            // With std::vector, out-of-bounds operations are automatically mitigated,
                            // as the container manages its memory footprint, resizing when necessary.
      
                            // CWE-401: Prevent memory leaks.
                            // As std::vector automatically deallocates its memory upon going out of scope,
                            // this avoids the common memory leaks associated with manual memory management in C++.
        
                            // CWE-676: Reduce the use of potentially dangerous functions.
                            // By eliminating the use of 'malloc' and 'free', we remove the potential risks
                            // these functions bring, such as improper memory management and dereferencing issues.

                            // CWE-805: Avoid buffer access with incorrect length value.
                            // The std::vector manages its own size and provides bounds checking, which
                            // prevents accessing out of its bounds, a common issue with manual array management.
                            std::vector<int> arr(arrSizeInput);
                            for (int i = 0; i < arrSizeInput; i++) 
                            {
                                cout << "Input number " << i + 1 << " to be added: " << endl;
                                cin >> arr[i];
                            }
                            yesOrNo = "T";
                            while(yesOrNo == "T") 
                            {
                                cout << "Would you like to change any number being added? (T= Yes/F = No)"<< endl;
                                cin >> yesOrNo;
                                if(yesOrNo == "T") 
                                {
                                    cout << "What number position would you like to change? (i.e. 1, 2, 3, etc.)"<< endl;
                                    cin >> numChange;
                                    numChange--;

                                    if(numChange >= 0 && numChange < arrSizeInput) 
                                    {
                                        cout << "Please input a replacement number: " << endl;
                                        cin >> replaceNum;

                                        if(replaceNum < std::numeric_limits<short>::max()) 
                                        {
                                            replaceShort = (short)replaceNum;
                                            arr[numChange] = replaceShort;
                                        }
                                        else 
                                        {
                                            arr[numChange] = replaceNum;
                                        }
                                    }
                                    else 
                                    {
                                        std::cerr << "The number you have selected is out of bounds."<< std::endl;
                                        yesOrNo = "T";
                                    }
                                }
                            }
                            
                            addTotal = 0;
                            // CWE-125
                            for(int num : arr)
                            {
                                addTotal += num;
                            }
                            cout << "Result: " << addTotal << endl;
                            }
                        else 
                        {
                            std::cerr << "There are too few many numbers that you would like to add." << std::endl;
                        }
                    }
                    else if(userInput == 7) {
                        cout << "How many numbers would you like to multiply? (Must be at least 2)" << endl;
                        cin >> arrSizeInput;
                        if(arrSizeInput >= 2) //CWE-192
                        {
                            arrSizePtr = &arrSizeInput;
                            arr = static_cast<int*>(malloc(sizeof(*arrSizePtr))); //CWE-467, CWE-170, CWE-122
                            if (arr == nullptr) {
                                std::cerr << "Memory allocation failed." << std::endl;
                            }
                            else
                            {
                                for(int i = 0; i < arrSizeInput; i++)
                                {
                                    cout << "Input number " << i + 1 << " to be multiplied: " << endl;
                                    cin >> arr[i];
                                }
                                while(yesOrNo == "T"){
                                    cout << "Would you like to change any number being multiplied? (T = Yes/F = No)"<< endl;
                                    cin >> yesOrNo;
                                    if(yesOrNo.size() == 1)
                                    {
                                        if(yesOrNo == "T")
                                        {
                                            cout << "What number position would you like to change? (i.e. 1, 2, 3, etc.)"<< endl;
                                            cin >> numChange;
                                            numChange--;
                                            if(numChange >= 0 && numChange < arrSizeInput) //CWE-129
                                            {
                                                cout << "Please input a replacement number: "<< endl;
                                                cin >> replaceNum;
                                                if(replaceNum < std::numeric_limits<short>::max()) // CWE-197
                                                {
                                                    replaceShort = (short)replaceNum;
                                                    arr[numChange] = replaceShort;
                                                }
                                                else
                                                    arr[numChange] = replaceNum;
                                            }
                                            else
                                            {
                                                std::cerr << "The number you have selected is out of bounds." << std::endl;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        std::cerr << "Please input either T for yes or F for no." << std::endl;
                                        yesOrNo = "T";
                                    }
                                }
                                for(int i = 0; i < arrSizeInput; i++)
                                {
                                    multiTotal = multiTotal * arr[i];
                                }
                                cout << "Result: ";
                                cout << multiTotal << endl;
                            }
                            multiTotal = 1;
                            yesOrNo = "T";
                            arr = NULL;
                        }
                        else
                        {
                            std::cerr << "There are too few many numbers that you would like to multiply." << std::endl;
                        }
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
    free(arr); //CWE-416
    arr = NULL; //CWE-415
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
