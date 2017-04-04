"""
Script to control an 8-channel relay via command line by
issuing single commands.
Takes 2 args, channel and state, which determine the channel
to be modified and whether it is to be turned on or off.
Ex: 'python relay_controller.py 1 0' turns channel 1 on.

Andrew Smith (2017)
"""

import RPi.GPIO as GPIO
import sys

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

def main():

	PINS = dict([(1, 17), (2, 18), (3, 27), (4, 22), 
        	(5, 23), (6, 24), (7, 25), (8, 21)])

	channel = int(sys.argv[1])
	state = int(sys.argv[2])
	pin = 1

	for c, p in PINS.items():
		if c == channel:
			pin = p

	GPIO.setup(pin, GPIO.OUT)
	GPIO.output(pin, state)

if __name__ == '__main__':
    main()