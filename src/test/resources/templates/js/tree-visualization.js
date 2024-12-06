// Place this in src/main/resources/static/js/tree-visualization.js

class TreeVisualizer {
    constructor(containerId) {
        this.containerId = containerId;
        this.duration = 750;
        this.margin = { top: 20, right: 90, bottom: 30, left: 90 };
        this.width = 800 - this.margin.left - this.margin.right;
        this.height = 500 - this.margin.top - this.margin.bottom;

        this.svg = d3.select(`#${containerId}`)
            .append("svg")
            .attr("width", this.width + this.margin.left + this.margin.right)
            .attr("height", this.height + this.margin.top + this.margin.bottom)
            .append("g")
            .attr("transform", `translate(${this.margin.left},${this.margin.top})`);

        this.tooltip = d3.select(`#${containerId}`)
            .append("div")
            .attr("class", "tooltip")
            .style("opacity", 0);
    }

    visualize(treeData, statistics) {
        const hierarchy = this.createHierarchy(treeData);
        const tree = d3.tree().size([this.height, this.width - 200]);
        const root = d3.hierarchy(hierarchy);
        const treeLayout = tree(root);

        // Links
        const link = this.svg.selectAll(".link")
            .data(treeLayout.links())
            .join("path")
            .attr("class", "link")
            .attr("d", d3.linkHorizontal()
                .x(d => d.y)
                .y(d => d.x));

        // Nodes
        const node = this.svg.selectAll(".node")
            .data(treeLayout.descendants())
            .join("g")
            .attr("class", "node")
            .attr("transform", d => `translate(${d.y},${d.x})`);

        // Node circles
        node.append("circle")
            .attr("r", 0)
            .style("fill", d => d.data.color)
            .transition()
            .duration(this.duration)
            .attr("r", 10);

        // Node text
        node.append("text")
            .attr("dy", ".35em")
            .attr("x", d => d.children ? -13 : 13)
            .style("text-anchor", d => d.children ? "end" : "start")
            .text(d => d.data.name)
            .style("fill-opacity", 0)
            .transition()
            .duration(this.duration)
            .style("fill-opacity", 1);

        // Interactive features
        node.on("mouseover", (event, d) => {
            this.tooltip.transition()
                .duration(200)
                .style("opacity", .9);
            this.tooltip.html(this.createTooltipContent(d, statistics))
                .style("left", (event.pageX + 10) + "px")
                .style("top", (event.pageY - 28) + "px");
        })
        .on("mouseout", () => {
            this.tooltip.transition()
                .duration(500)
                .style("opacity", 0);
        })
        .on("click", (event, d) => this.handleNodeClick(d));

        // Add statistics panel
        this.updateStatisticsPanel(statistics);
    }

    createHierarchy(treeData) {
        const nodes = treeData.nodes;
        if (!nodes.length) return null;

        const createNode = (index) => {
            if (index === null || index >= nodes.length) return null;
            const node = nodes[index];
            return {
                name: node.value.toString(),
                color: node.color,
                balanceFactor: node.balanceFactor,
                height: node.height,
                children: [
                    node.leftIndex !== null ? createNode(node.leftIndex) : null,
                    node.rightIndex !== null ? createNode(node.rightIndex) : null
                ].filter(n => n !== null)
            };
        };

        return createNode(0);
    }

    createTooltipContent(d, statistics) {
        return `
            <div class="tooltip-content">
                <strong>Value:</strong> ${d.data.name}<br>
                <strong>Balance Factor:</strong> ${d.data.balanceFactor}<br>
                <strong>Height:</strong> ${d.data.height}<br>
            </div>
        `;
    }

    handleNodeClick(d) {
        // Highlight path to root
        this.svg.selectAll(".node circle")
            .style("stroke-width", node =>
                this.isAncestor(d, node) ? "4px" : "2px");
    }

    isAncestor(node, potentialAncestor) {
        while (node) {
            if (node === potentialAncestor) return true;
            node = node.parent;
        }
        return false;
    }

    updateStatisticsPanel(statistics) {
        const statsPanel = d3.select(`#${this.containerId}-stats`)
            .html(`
                <div class="stats-panel">
                    <h3>Tree Statistics</h3>
                    <p>Nodes: ${statistics.nodeCount}</p>
                    <p>Height: ${statistics.height}</p>
                    <p>Balanced: ${statistics.isBalanced ? "Yes" : "No"}</p>
                    <p>Leaf Nodes: ${statistics.leafNodes}</p>
                    <p>Internal Nodes: ${statistics.internalNodes}</p>

                    <h4>Traversals</h4>
                    ${statistics.traversalResults.map(t => `<p>${t}</p>`).join('')}

                    <h4>Construction Steps</h4>
                    <ol>
                        ${statistics.operationSteps.map(step => `<li>${step}</li>`).join('')}
                    </ol>
                </div>
            `);
    }
}