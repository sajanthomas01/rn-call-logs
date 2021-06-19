# rn-call-logs

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

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

[readme made with - makereadme.com](https://www.makeareadme.com/)
## License
[MIT](https://choosealicense.com/licenses/mit/)