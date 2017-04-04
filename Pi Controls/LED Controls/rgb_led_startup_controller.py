"""
Script to cycle (3) RGB LEDs through the available range
of colors for 1 minute, after which rgb_led_mode_controller.py
is called, setting the LEDs to a default of RED.

Andrew Smith (2017)
"""

import random, time
import RPi.GPIO as GPIO
import colorsys
import os

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

def color_wheel(pos):

    if pos < 0:
        pos = 0

    if pos > 384:
        pos = 384

    if pos < 128:
        r = 127 - pos % 128
        g = pos % 128
        b = 0

    elif pos < 256:
        g = 127 - pos % 128
        b = pos % 128
        r = 0

    else:
        b = 127 - pos % 128
        r = pos % 128
        g = 0

    return r, g, b

def cycle_color():

	RED1 = GPIO.PWM(20, 100)
	RED1.start(100)
	GREEN1 = GPIO.PWM(12, 100)
	GREEN1.start(100)
	BLUE1 = GPIO.PWM(16, 100)
	BLUE1.start(100)
	RED2 = GPIO.PWM(4, 100)
	RED2.start(100)
	GREEN2 = GPIO.PWM(6, 100)
	GREEN2.start(100)
	BLUE2 = GPIO.PWM(5, 100)
	BLUE2.start(100)
	RED3 = GPIO.PWM(19, 100)
	RED3.start(100)
	GREEN3 = GPIO.PWM(26, 100)
	GREEN3.start(100)
	BLUE3 = GPIO.PWM(13, 100)
	BLUE3.start(100)

    timeout = time.time() + 60
    while time.time() < timeout:
        for pos in range(0,384):
            r, g, b = color_wheel(pos)
            RED1.ChangeDutyCycle((r/128.0)*100.0)
            RED2.ChangeDutyCycle((r/128.0)*100.0)
            RED3.ChangeDutyCycle((r/128.0)*100.0)
            GREEN1.ChangeDutyCycle((g/128.0)*100.0)
            GREEN2.ChangeDutyCycle((g/128.0)*100.0)
            GREEN3.ChangeDutyCycle((g/128.0)*100.0)
            BLUE1.ChangeDutyCycle((b/128.0)*100.0)
            BLUE2.ChangeDutyCycle((b/128.0)*100.0)
            BLUE3.ChangeDutyCycle((b/128.0)*100.0)
            time.sleep(.1)

def main():

    L1_PINS = [20, 12, 16]
    L2_PINS = [4, 6, 5]
    L3_PINS = [19, 26, 13]

    for i in range(0, 3):
        GPIO.setup(L1_PINS[i], GPIO.OUT)
        GPIO.setup(L2_PINS[i], GPIO.OUT)
        GPIO.setup(L3_PINS[i], GPIO.OUT)

    cycle_color()
    os.system("python rgb_led_mode_controller.py r")

if __name__ == '__main__':
    main()