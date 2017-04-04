"""
Script to initialize GPIO pins at startup.
Started by relay_startup.sh
By Andrew Smith (2016)
"""

import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

GPIO.setup(17, GPIO.OUT)
GPIO.setup(18, GPIO.OUT)
GPIO.setup(21, GPIO.OUT)
GPIO.setup(22, GPIO.OUT)
GPIO.setup(23, GPIO.OUT)
GPIO.setup(24, GPIO.OUT)
GPIO.setup(25, GPIO.OUT)
GPIO.setup(27, GPIO.OUT)

GPIO.setup(16, GPIO.OUT)
GPIO.setup(20, GPIO.OUT)

GPIO.output(17, 1)
GPIO.output(18, 1)
GPIO.output(21, 1)
GPIO.output(22, 1)
GPIO.output(23, 1)
GPIO.output(24, 1)
GPIO.output(25, 1)
GPIO.output(27, 1)

GPIO.output(16, 0)
GPIO.output(20, 0)