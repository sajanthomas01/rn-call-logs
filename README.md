# rn-call-logs  [NOT ACTIVELY MAINTAINED]

------------
**Notice:** This package is no longer maintained.

####  I am no longer actively maintaining this repository, and there will be no further contributions or updates from my side. Please consider migrating to an alternative package that suits your needs.

------------

Simple react-native package for reading call logs from Android Devices(**Only works with Android**)

## Installation

Use the package manager yarn or npm to install **rn-call-logs**.

```bash
yarn add rn-call-logs
```
```bash
npm install rn-call-logs
```
### Android
The package is meant for react-native 0.60+, and should auto link everything. if auto-linking fails then will have to manually link things as in the old days :D
## Usage

```js
import * as React from 'react';

import {
  StyleSheet,
  View,
  Text,
  PermissionsAndroid,
  FlatList,
  TouchableWithoutFeedback,
} from 'react-native';
import {
  getAllLogs,
  getOutgoingLogs,
  getIncomingLogs,
  getMissedLogs,
  getRejectedLogs,
  getBlockedLogs,
  getExternallyAnsweredLogs,
  getByNumber,
  getNotConnectedLogs
} from 'rn-call-logs';

export default function App() {

  //interfaces
  interface logResponse {
    number: string;
    date: string;
    duration: string;
    country: string;
    type: string;
  }

  const [logs, setLogs] = React.useState<[logResponse] | []>();

  const getLogs = async () => {
    try {
      const granted = await PermissionsAndroid.request(
        PermissionsAndroid.PERMISSIONS.READ_CALL_LOG,
        {
          title: 'RN Call Logs',
          message: 'Access your call logs',
          buttonNeutral: 'Ask Me Later',
          buttonNegative: 'Cancel',
          buttonPositive: 'OK',
        }
      );
      if (granted === PermissionsAndroid.RESULTS.GRANTED) {
        try {
          const response: [logResponse] | [] = await getAllLogs({
            fromEpoch: 0,
            toEpoch: 0,
            limit: 10,
          });
          setLogs(response);
        } catch (error) {
          console.log(error);
        }
      } else {
        console.log('Call Log permission denied');
      }
    } catch (e) {
      console.log(e);
    }
  };

  React.useEffect(() => {
    getLogs();
  }, []);

  // UI Snippet from : https://www.itechinsiders.com/how-to-design-card-in-react-native/
  const renderItem = ({ item }: any) => (
    <TouchableWithoutFeedback>
      <View style={styles.mainCardView}>
        <View style={styles.cardInnerViewOne}>
          <View>
            <Text style={styles.cardMainText}>
              {item.number} ({item.country})
            </Text>
            <Text style={styles.cardSubText}>{item.type}</Text>
            <Text style={styles.cardSubText}>
              Duration: {item.duration} sec
            </Text>
            <Text style={styles.cardSubText}>
              Date:{new Date(parseInt(item.date)).toString()}
            </Text>
          </View>
        </View>
      </View>
    </TouchableWithoutFeedback>
  );

  return (
    <View style={styles.container}>
      <Text>Result:</Text>
      <FlatList
        data={logs}
        renderItem={renderItem}
        keyExtractor={(item) => item.date}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
  cardInnerViewOne: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  cardMainText: {
    fontSize: 14,
    color: 'black',
    fontWeight: 'bold',
    textTransform: 'capitalize',
  },
  cardSubText: {
    color: 'gray',
    fontSize: 12,
  },
  mainCardView: {
    height: 90,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'white',
    borderRadius: 15,
    shadowColor: 'gray',
    shadowOffset: { width: 0, height: 0 },
    shadowOpacity: 1,
    shadowRadius: 8,
    elevation: 8,
    flexDirection: 'row',
    paddingLeft: 16,
    paddingRight: 14,
    margin: 10,
    minWidth: '90%',
  },
});

```
## Response format on success
```
[
 {
    number: string; // phone number
    date: string; // epoch timestamp
    duration: string; // duration in seconds
    country: string; // 2 letter country code 
    type: string; // type of call ( 'INCOMING', 'OUTGOING','MISSED', 'VOICEMAIL',  
                                    'REJECTED', 'BLOCKED', 'EXTERNAL')
  }
]
```

## Available Methods
* getAllLogs()

```js
getAllLogs({ fromEpoch: 0,toEpoch: 0, }); 
// fromEpoch and toEpoch are mandatory fields to get all data pass 0 as value for each.
// to get data in a date range pass epoch time in milliseconds
```
* getOutgoingLogs()
```js
getOutgoingLogs({ fromEpoch: 0,toEpoch: 0, }); 
// fromEpoch and toEpoch are mandatory fields to get all data pass 0 as value for each.
// to get data in a date range pass epoch time in milliseconds
```
* getIncomingLogs()
```js
getIncomingLogs({ fromEpoch: 0,toEpoch: 0, }); 
// fromEpoch and toEpoch are mandatory fields to get all data pass 0 as value for each.
// to get data in a date range pass epoch time in milliseconds
```
* getMissedLogs()
```js
getMissedLogs({ fromEpoch: 0,toEpoch: 0, }); 
// fromEpoch and toEpoch are mandatory fields to get all data pass 0 as value for each.
// to get data in a date range pass epoch time in milliseconds
```
* getRejectedLogs()
```js
getRejectedLogs({ fromEpoch: 0,toEpoch: 0, }); 
// fromEpoch and toEpoch are mandatory fields to get all data pass 0 as value for each.
// to get data in a date range pass epoch time in milliseconds
```
* getBlockedLogs()
```js
getBlockedLogs({ fromEpoch: 0,toEpoch: 0, }); 
// fromEpoch and toEpoch are mandatory fields to get all data pass 0 as value for each.
// to get data in a date range pass epoch time in milliseconds
```
* getExternallyAnsweredLogs()
```js
getExternallyAnsweredLogs({ fromEpoch: 0,toEpoch: 0, }); 
// fromEpoch and toEpoch are mandatory fields to get all data pass 0 as value for each.
// to get data in a date range pass epoch time in milliseconds
```
* getNotConnectedLogs() //calls you made but didn't connected
```js
getNotConnectedLogs({ fromEpoch: 0,toEpoch: 0, }); 
// fromEpoch and toEpoch are mandatory fields to get all data pass 0 as value for each.
// to get data in a date range pass epoch time in milliseconds
```
* getByNumber()
```js
getByNumber({ fromEpoch: 0,toEpoch: 0,phoneNumber: 123456789 }); 
// fromEpoch, toEpoch and phoneNumber are mandatory fields 
// to get all data pass 0 as value for fromEpoch and toEpoch.
// to get data in a date range pass epoch time in milliseconds
```
## Options
| key                      | type   | Required | Description                                                                                                                            |
|--------------------------|--------|----------|----------------------------------------------------------------------------------------------------------------------------------------|
| fromEpoch                | Number | true     | Starting time in epoch (milliseconds) for date  based filtering, pass 0 for retrieving all data                                        |
| toEpoch                  | Number | true     | Endingtime in epoch (milliseconds) for date  based filtering, pass 0 for retrieving all data                                           |
| limit                    | Number | optional | To limit the results                                                                                                                   |
| skip                     | Number | optional | To skip rows from result                                                                                                               |
| phoneNumber(getByNumber) | String | true     | To search with a specific mobile number(only for getByNumber, do remember to omit country codes for getting better results)            |
| type(getByNumber)        | Enum   | Optional |  'INCOMING', 'OUTGOING', 'MISSED', 'VOICEMAIL',  'REJECTED', 'BLOCKED', 'EXTERNAL', 'ALL'  To reterive only results of a specific type |

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.


[readme made with - makereadme.com](https://www.makeareadme.com/)
## License
[MIT](https://choosealicense.com/licenses/mit/)
