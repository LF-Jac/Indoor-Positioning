# Indoor Positioning

Proposed solution uses smartphones for sensors data acquiring and processing. In PDR technique, data is processed to obtain step detection, step length estimation and heading orientation. At the same time, Wi-Fi RSSI of all detected access points will be recorded.
Correction points were added for calibration towards PDR technique to reduce error accumulation over time.
When PDR is unable to confidently provide user's position, Wi-Fi fingerprinting will take over.


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Using Android sensor framework to access to many types of sensors in smartphones. Check for these sensor types avaibility and support in the smartphone.

```
TYPE_ACCELEROMETER
TYPE_GYROSCOPE
TYPE_MAGNETIC_FIELD
TYPE_LINEAR_ACCELERATION
```

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* paul lawitzki (http://plaw.info/articles/sensorfusion/#articles)

