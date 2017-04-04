"""
Script to set (3) RGB LEDs to 1 of 7 colors via RPi's GPIO pins.
Takes a single char corresponding to the desired color as an arg.
Available args are [r, g, b, y, m, c, w].
Ex: 'python rgb_led_mode_controller.py r' sets the LEDs to RED.

Andrew Smith (2017)
"""

import RPi.GPIO as GPIO
import sys

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

L1_PINS = [20, 12, 16]
L2_PINS = [4, 6, 5]
L3_PINS = [19, 26, 13]

RED = [1, 0, 0]
BLUE = [0, 0, 1]
GREEN = [0, 1, 0]
YELLOW = [1, 1, 0]
MAGENTA = [1, 0, 1]
CYAN = [0, 1, 1]
WHITE = [1, 1, 1]

COLORS = dict([('r', RED), ('b', BLUE), ('g', GREEN), ('y', YELLOW), 
        	('m', MAGENTA), ('c', CYAN), ('w', WHITE)])

def set_color(COLOR):

	for i in range(0, 3):
		GPIO.setup(L1_PINS[i], GPIO.OUT)
		GPIO.setup(L2_PINS[i], GPIO.OUT)
		GPIO.setup(L3_PINS[i], GPIO.OUT)
		GPIO.output(L1_PINS[i], COLOR[i])
		GPIO.output(L2_PINS[i], COLOR[i])
		GPIO.output(L3_PINS[i], COLOR[i])
		
def main():

	for c, a in COLORS.items():
		if c == sys.argv[1]:
			set_color(a)

if __name__ == '__main__':
    main()