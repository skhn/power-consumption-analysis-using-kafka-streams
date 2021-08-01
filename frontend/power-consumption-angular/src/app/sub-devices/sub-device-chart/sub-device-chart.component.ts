import { Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import * as d3 from 'd3';
import { SubDevices } from '../sub-devices.model';

@Component({
  selector: 'app-sub-device-chart',
  templateUrl: './sub-device-chart.component.html',
  styleUrls: ['./sub-device-chart.component.css']
})
export class SubDeviceChartComponent implements OnInit, OnChanges {

    private width = 500;
    private height = 350;
    private margin = 50;
    public svg;
    public svgInner;
    public yScale;
    public xScale;
    public xAxis;
    public yAxis;
    public lineGroup;

    constructor(public chartElem: ElementRef) { }

    @Input() data: SubDevices[] = [];

  ngOnInit(): void {
  }

  public ngOnChanges(changes: SimpleChanges): void {
    if (this.data.length < 1) {

        this.svg = d3
            .select(this.chartElem.nativeElement)
            .select('.linechart')
            .append('svg')
            .attr('id', 'SVGOne')
            .attr('height', this.height)
            .attr('width', this.width);


        this.initializeChart(this.data);
        this.drawChart(this.data);
    }
    if (changes.hasOwnProperty('data') && this.data) {
        //   this.svg.selectAll("*").remove();
        this.initializeChart(this.data);
        this.drawChart(this.data);
        console.log(changes)
        window.addEventListener('resize', () => this.drawChart(this.data));
    }
      
  }


  private initializeChart(data: SubDevices[]): void {

    this.svg = d3.select('#SVGOne').selectAll('*').remove();

    this.svgInner =
    d3.select('#SVGOne')
        .append('g')
        .style('transform', 'translate(' + this.margin + 'px, ' + this.margin + 'px)');

    this.yScale = d3
        .scaleLinear()
        .domain([d3.max(data, d =>  d.Sub_metering_2) + 1, d3.min(data, d => d.Sub_metering_2) - 1])
        .range([0, this.height - 2 * this.margin]);

    this.yAxis = this.svgInner
        .append('g')
        .attr('id', 'y-axis')
        .style('transform', 'translate(' + this.margin + 'px,  0)');

    this.xScale = d3.scaleTime().domain(d3.extent(data, d => new Date(d.Date)));

    this.xAxis = this.svgInner
        .append('g')
        .attr('id', 'x-axis')
        .style('transform', 'translate(0, ' + (this.height - 2 * this.margin) + 'px)');

    this.lineGroup = this.svgInner
        .append('g')
        .append('path')
        .attr('id', 'line')
        .style('fill', 'none')
        .style('stroke', 'gray')
        .style('stroke-width', '2px')
}

private drawChart(data: SubDevices[]): void {
    // this.width = this.chartElem.nativeElement.getBoundingClientRect().width;
    // this.svg
    // .attr("width", "90%")
    // .attr("viewBox", "0 0 960 500")
    // .attr("preserveAspectRatio", "none");

    this.xScale.range([this.margin, this.width - 2 * this.margin]);

    const xAxis = d3
        .axisBottom(this.xScale)
        .ticks(10)
        .tickFormat(d3.timeFormat('%m/%Y'));

    this.xAxis.call(xAxis);

    const yAxis = d3
        .axisLeft(this.yScale);

    this.yAxis.call(yAxis);

    const line = d3
        .line()
        .x(d => d[0])
        .y(d => d[1])
        .curve(d3.curveMonotoneX);

    const points: [number, number][] = data.map(d => [
        this.xScale(new Date(d.Date)),
        this.yScale(d.Sub_metering_2),
    ]);

    this.lineGroup.attr('d', line(points));
}

}
