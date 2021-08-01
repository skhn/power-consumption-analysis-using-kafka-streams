const PROXY_CONFIG = [
    {
        context: [
    "/v1/energy-data-stream"
        ],
        target: "http://localhost:8004",
        secure: false
    }
]

module.exports = PROXY_CONFIG;