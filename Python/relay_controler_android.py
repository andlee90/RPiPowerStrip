"""
Script to control an 8-channel relay via RPiPowerStrip Android
Application

By Andrew Smith (2017)
"""

import RPi.GPIO as GPIO
import sys

PINS = dict([(1, 17), (2, 18), (3, 27), (4, 22), 
        	(5, 23), (6, 24), (7, 25), (8, 21)])

channel = int(sys.argv[1])
state = int(sys.argv[2])
pin = 1

for c, p in PINS.items():
	if c == channel:
		pin = p
		
# For testing
#print 'channel : %d\nstate : %d\npin : %d' % (channel, state, pin)

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(pin, GPIO.OUT)
GPIO.output(pin, state)