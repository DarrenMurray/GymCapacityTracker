'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import Recharts from 'recharts';
import {LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend} from 'recharts';

const GET_ALL = "/api/capacities";

class SimpleLineChart extends React.Component {
  constructor(props){
   super(props)
   this.state={
     requestFailed: false
   }
  }

  componentDidMount() {
    fetch(GET_ALL)
    .then(response =>{
      if(!response.ok){
      throw Error("Network request failed")
      }
      return response
    })
    .then(response => response.json())
    .then(response => {
      this.setState({
          gymDayData: response
        })
     }, () => {
      this.setState({requestFailed:true})
     })
     }

	render () {
  	return (
    	<LineChart width={1200} height={600} data={this.state.gymDayData}
            margin={{top: 5, right: 30, left: 20, bottom: 5}}>

       <XAxis dataKey="timestamp" />
       <YAxis/>
       <Tooltip/>
       <Legend />
       <Line type="monotone" dataKey="currentUsers" stroke="#8884d8"/>
      </LineChart>
    );
  }
}

export default SimpleLineChart;
