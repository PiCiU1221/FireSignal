# FireSignal
Volunteer fire department system that emulates emergency dispatch management. It enables the dispatcher to input an address, geocode it for mapping, add a description and then select the available fire departments to respond to the incident. The dispatcher receives their calculated distance to the target and an approximate arrival time from an external API.

Firefighters belonging to the selected fire departments, upon logging into the desktop app (simulating a mobile app), instantly receive alarm notifications via Server-Sent Events, delivering messages in real-time. They can then decide whether they can respond to the call. The app allows them to check the number of alerted firefighters, see how many have accepted the call, determine if there's a commander or a driver, and access details such as the fire description and address.

This system enables volunteer fire departments to respond much more quickly than relying on the fire chief to receive alarm information and then individually call or text each firefighter in the department, a process that takes way too long.

## Test Account Details

- **Username:** admin
- **Password:** admin
