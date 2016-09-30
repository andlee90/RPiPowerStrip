"""
Script to control an 8-channel relay
By Andrew Smith (2016)
"""

import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

def controller(SOCKET_NUM, STATE):

	pins = dict([(1, 17), (2, 18), (3, 27), (4, 22), 
        	(5, 23), (6, 24), (7, 25), (8, 21)])

	if STATE == 'OFF' or STATE == 'off':
    		for s_n, p in pins.items():
            		if s_n == SOCKET_NUM:
                		GPIO.setup(p, GPIO.OUT)
                		GPIO.output(p, 1)

   	elif STATE == 'ON' or STATE == 'on':
    		for s_n, p in pins.items():
            		if s_n == SOCKET_NUM:
                		GPIO.setup(p, GPIO.OUT)
                		GPIO.output(p, 0)

    	else:
    		print "Invalid Input\n"

TITLE = """
 _____________________
| Relay Controls v1.0 |
| by Andrew Smith     |
|_____________________|"""

MENU = """
To toggle a socket on or off, enter the 
socket number and the socket state in the 
following format: <SOCKET_NUM> <STATE>

Enter 'quit' to terminate, keeping changes

--- or ---

Enter 'revert' to terminate, abandoning changes

--- or ---

Enter 'menu' to repeat this menu
"""

def main():

	print TITLE
	print MENU

	REPEAT = 1

	while REPEAT != 0:

    		INPUT = raw_input(">> ")
    		if INPUT == 'quit' or INPUT == 'q':
    			REPEAT = 0
    			break

    		elif INPUT == 'revert' or INPUT == 'r':
    			REPEAT = 0
    			GPIO.cleanup()
    			break

    		elif INPUT == 'menu' or INPUT == 'r':
    			print MENU

    		elif INPUT[0].isdigit():
    			PARAMS = INPUT.split(' ')
    			SIZE = len(PARAMS)

    			if SIZE == 2:
    				SOCKET_NUM = int(PARAMS[0])
    				STATE = PARAMS[1]
    				controller(SOCKET_NUM, STATE)

			else:
				print "Invalid Input\n"
                
		else:
			print "Invalid Input\n"

if __name__ == '__main__':
    main()
