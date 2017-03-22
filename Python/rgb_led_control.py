"""
Script to control rgb led via RPi's GPIO pins
By Andrew Smith (2016)
"""

import random, time
import RPi.GPIO as GPIO
import colorsys

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

GPIO.setup(13, GPIO.OUT)
GPIO.setup(19, GPIO.OUT)
GPIO.setup(26, GPIO.OUT)

RED = GPIO.PWM(13, 100)
RED.start(100)

GREEN = GPIO.PWM(19, 100)
GREEN.start(100)

BLUE = GPIO.PWM(26, 100)
BLUE.start(100)

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

try:
   while(True):
      for pos in range(0,384):
         r, g, b = color_wheel(pos)

         RED.ChangeDutyCycle((r/128.0)*100.0)
         GREEN.ChangeDutyCycle((g/128.0)*100.0)
         BLUE.ChangeDutyCycle((b/128.0)*100.0)

         time.sleep(1)

except KeyboardInterrupt:
   GPIO.cleanup()