import pandas as pd
import matplotlib.pyplot as plt

# Load results
df = pd.read_csv("density_results.csv")

# Group by density (SearchFactor)
grouped = df.groupby("SearchFactor").mean().reset_index()

# Plot runtimes
plt.plot(grouped["SearchFactor"], grouped["SerialTime"], marker='o', label="Serial")
plt.plot(grouped["SearchFactor"], grouped["ParallelTime"], marker='o', label="Parallel")

plt.xlabel("Search Density")
plt.ylabel("Runtime (ms)")
plt.title("Runtime vs Density")
plt.legend()
plt.grid(True)
plt.show()
